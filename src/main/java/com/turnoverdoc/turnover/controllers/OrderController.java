package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.BankDetailsDto;
import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.dto.order.ConfirmDocsDto;
import com.turnoverdoc.turnover.dto.order.OrderDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.BankDetailsService;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.OrderService;
import com.turnoverdoc.turnover.services.UserService;
import com.turnoverdoc.turnover.services.impl.file_service.DocumentFiles;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.turnoverdoc.turnover.error.ErrorsContainer.*;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private OrderService orderService;
    private UserService userService;
    private ContactService contactService;
    private BankDetailsService bankDetailsService;

    @Autowired
    public void setBankDetailsService(BankDetailsService bankDetailsService) {
        this.bankDetailsService = bankDetailsService;
    }

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }


    //TODO: replace all conditions to call a method from a class between controller
    // and logical classes and try catch statement

    @RequestMapping(value = "/uploadDocs/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("CONTRACT") MultipartFile contract,
                                                   @RequestParam("PASSPORT") MultipartFile passport,
                                                   @RequestParam(value = "P_45", required = false) MultipartFile p45,
                                                   @RequestParam(value = "P_60", required = false) MultipartFile p60,
                                                   @RequestParam(value = "P_80", required = false) MultipartFile p80,
                                                   Principal principal, @PathVariable String orderId) throws ErrorDto {

        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
            Order order = orderService.findById(Long.parseLong(orderId));

            if (order != null && user != null && order.getUser().getId().equals(user.getId())) {
                if (order.getStatus().equals(OrderStatus.REQUEST_FOR_DOCS)) {
                    MultipartFile[] files = new MultipartFile[]{contract, passport, p45, p60, p80};
                    boolean filesUploadedSuccess = orderService.saveOrderFiles(files, user, order);

                    if (!filesUploadedSuccess) {
                        throw TURN_01;
                    }
                    return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
                }
                throw TURN_04;
            }
            throw TURN_02;
        }
        throw AUTH_03;
    }

    @PostMapping("/sendBankDetails")
    public ResponseEntity<String> sendBankDetails(@RequestBody BankDetailsDto bankDetailsDto, Principal principal) throws ErrorDto {
        Order order = orderService.findById(Long.parseLong(bankDetailsDto.getOrderId()));

        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());

            if (order != null && user != null && order.getUser().getId().equals(user.getId())) {
                if (order.getStatus().equals(OrderStatus.CHEQUE_BANKED)) {
                    bankDetailsService.send(bankDetailsDto, order);
                    return new ResponseEntity<>("Operation successfully implemented", HttpStatus.OK);
                }
                throw TURN_04;
            }
            throw TURN_02;
        }
        throw AUTH_03;
    }

    @PostMapping("/confirmDocs/{orderId}")
    public ResponseEntity<String> confirmDocsReceive(Principal principal, @RequestBody ConfirmDocsDto confirmDocsDto) throws ErrorDto {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            Order order = orderService.findById(Long.parseLong(confirmDocsDto.getOrderId()));

            if (order != null && user != null && order.getUser().getId().equals(user.getId())) {
                if (order.getContractPath() != null &&
                        order.getP45Path() != null &&
                        order.getP60Path() != null &&
                        order.getP80Path() != null &&
                        order.getPassportPath() != null) {
                    if (order.getStatus().equals(OrderStatus.REQUEST_FOR_DOCS)) {
                        order.getContact().setMessengerNotify(confirmDocsDto.isMessengerNotify());
                        orderService.changeStatus(order, OrderStatus.ALL_DOCS_RECEIVED);
                        return new ResponseEntity<>("Docs successfully confirmed", HttpStatus.OK);
                    }
                    throw TURN_04;
                }
            }
            throw TURN_02;
        }
        throw AUTH_03;
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrderAndContacts(@RequestBody ContactDto contactDto, Principal principal) throws ErrorDto {
        if (principal != null) {
            // first step, where user send his contacts
            User user = userService.findByUsername(principal.getName());
            if (user != null) {
                Contact contact = contactService.addContact(contactDto);
                Order order = orderService.createOrder(user, contact);

                return new ResponseEntity<>("Order with contacts successfully created with order id - " + order.getId(), HttpStatus.OK);
            }
            throw TURN_02;
        }

        throw AUTH_02;
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<OrderDto>> getAllOrdersForUser(Principal principal) throws ErrorDto {
        if (principal != null) {
            User user = userService.findByUsername(principal.getName());
            List<Order> orders = new ArrayList<>();

            if (user != null) {
                orders = orderService.getAllForUser(user.getId());
                return new ResponseEntity<>(OrderDto.toOrderDtoList(orders), HttpStatus.OK);
            }
            throw TURN_02;
        }
        throw AUTH_02;
    }

    //TODO:When there is a frontend - rewrite to Post!!! (For ease of testing, I made a get)
    @GetMapping("/download/{orderId}/{documentFile}")
    public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String orderId, @PathVariable DocumentFiles documentFile) throws Exception {
        File file = null;
        Order order = orderService.findById(Long.valueOf(orderId));
        if (order != null) {
            file = orderService.getFileDocByOrder(order, documentFile.name());
        }

        if (file == null) {
            throw TURN_05;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.toURL().openConnection().getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + documentFile.name() + "." + com.google.common.io.Files.getFileExtension(file.getPath()))
                .body(new ByteArrayResource(Files.readAllBytes(file.toPath())));
    }

}
