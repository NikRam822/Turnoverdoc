package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.ContactDto;
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
    public @ResponseBody
    ResponseEntity<String> handleFileUpload(HttpServletRequest request,
                                            @ModelAttribute ContactDto requestDto,
                                            @RequestParam(value = "CONTRACT", required = false) MultipartFile contract,
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
        } else {
            contactService.addContact(new Contact(requestDto.getPhone(), requestDto.getEmail(), requestDto.getMessanger(), addedOrder));
            return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
        }


    }
}
