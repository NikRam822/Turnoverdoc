package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.dto.OrderDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.OrderService;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final Logger LOGGER = log;
    private OrderService orderService;
    private UserService userService;
    private ContactService contactService;

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

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResponseEntity<String> handleFileUpload(@RequestParam(value = "CONTRACT", required = false) MultipartFile contract,
                                            @RequestParam("PASSPORT") MultipartFile passport,
                                            @RequestParam(value = "P_45", required = false) MultipartFile p45,
                                            @RequestParam(value = "P_60", required = false) MultipartFile p60,
                                            @RequestParam(value = "P_80", required = false) MultipartFile p80,
                                            Principal principal) {

        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
        }
        Order addedOrder = orderService.addOrder(new Order(), user);

        MultipartFile[] files = new MultipartFile[]{contract, passport, p45, p60, p80};

        boolean filesUploadedSuccess = orderService.saveOrderFiles(files, user, addedOrder);

        if (!filesUploadedSuccess) {
            return new ResponseEntity<>("Failed to upload files", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
    }

    @PostMapping("/createOrder")
    public ResponseEntity<String> createOrderAndContacts(@ModelAttribute ContactDto contactDto, Principal principal) { // TODO: create name for every step(screen)
        // first step, where user send his contacts
        User user = userService.findByUsername(principal.getName());
        if (user != null) {
            Contact contact = contactService.addContact(contactDto);
            orderService.createOrder(user, contact);

            return new ResponseEntity<>("Contacts have been successfully send", HttpStatus.OK);
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
}
