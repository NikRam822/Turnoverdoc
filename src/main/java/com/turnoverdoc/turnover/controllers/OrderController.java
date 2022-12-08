package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.BankDetailsDto;
import com.turnoverdoc.turnover.dto.ChangeStatusDto;
import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.dto.FilterOrderDto;
import com.turnoverdoc.turnover.dto.order.FullOrderDto;
import com.turnoverdoc.turnover.dto.order.OrderDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.BankDetailsService;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.OrderService;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.turnoverdoc.turnover.error.ErrorsContainer.*;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final Logger LOGGER = log;
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

    @RequestMapping(value = "/uploadDocs/{orderId}", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam("CONTRACT") MultipartFile contract,
                                            @RequestParam("PASSPORT") MultipartFile passport,
                                            @RequestParam(value = "P_45", required = false) MultipartFile p45,
                                            @RequestParam(value = "P_60", required = false) MultipartFile p60,
                                            @RequestParam(value = "P_80", required = false) MultipartFile p80,
                                            Principal principal, @PathVariable String orderId) {

        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());

            MultipartFile[] files = new MultipartFile[]{contract, passport, p45, p60, p80};

            Order order = orderService.findById(Long.parseLong(orderId));
            if (order != null) {
                if (order.getUser().getId().equals(user.getId())) {
                    boolean filesUploadedSuccess = orderService.saveOrderFiles(files, user, order);

                    if (!filesUploadedSuccess) {
                        throw TURN1;
                    }
                    return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
                }
                throw TURN2;
            }
            throw TURN2;
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/changeStatus")
    public ResponseEntity<String> changeStatus(@RequestBody ChangeStatusDto changeStatusDto) {
        Order order = null;
        try {
            order = orderService.findById(Long.parseLong(changeStatusDto.getOrderId()));
        } catch (NumberFormatException e) {
            throw e;
            // TODO: Create exceptions for admin
        }

        if (order != null) {
            try {
                OrderStatus status = OrderStatus.valueOf(changeStatusDto.getStatus());
                order.setStatus(status);
                orderService.update(order);
                return new ResponseEntity<>("Order successful saved", HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                throw e;
                // TODO: Create exceptions for admin
            }
        }
        return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/sendBankDetails")
    public ResponseEntity<String> sendBankDetails(@RequestBody BankDetailsDto bankDetailsDto, Principal principal) {
        Order order = null;

        try {
            order = orderService.findById(Long.parseLong(bankDetailsDto.getOrderId()));
        } catch (NumberFormatException e) {
            throw e;
            // TODO: Create exceptions for user
        }

        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
        }

        if (user != null) {
            if (order != null) {
                if (order.getId().equals(user.getId())) {
                    if (bankDetailsService.isValid(bankDetailsDto)) {
                        bankDetailsService.send(bankDetailsDto, order);
                        return new ResponseEntity<>("Operation successfully implemented", HttpStatus.OK);
                    }
                    return new ResponseEntity<>("Invalid input data", HttpStatus.BAD_REQUEST);
                }
                return new ResponseEntity<>("Invalid orderId", HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>("Order not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrderAndContacts(@ModelAttribute ContactDto contactDto, Principal principal) {
        // first step, where user send his contacts
        User user = userService.findByUsername(principal.getName());
        if (user != null) {
            if (contactService.isValid(contactDto)) {
                Contact contact = contactService.addContact(contactDto);
                Order order = orderService.createOrder(user, contact);

                return new ResponseEntity<>("Order with contacts successfully created with order id - " + order.getId(), HttpStatus.OK);
            }
            return new ResponseEntity<>("Invalid contacts", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/get/all")
    public ResponseEntity<List<OrderDto>> getAllOrdersForUser(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        List<Order> orders = new ArrayList<>();

        if (user != null) {
            orders = orderService.getAllForUser(user.getId());
        }

        return new ResponseEntity<>(OrderDto.toOrderDtoList(orders), HttpStatus.OK);
    }

    @PostMapping("/get-filtered-orders")
    public ResponseEntity<List<FullOrderDto>> getFilteredOrders(@ModelAttribute FilterOrderDto filterDto){
        List<Order> orders = orderService.getFilteredOrders(filterDto);
        return new ResponseEntity<>(FullOrderDto.toFullOrderDtoList(orders), HttpStatus.OK);
    }
}
