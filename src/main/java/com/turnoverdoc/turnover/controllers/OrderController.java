package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.FileService;
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
    FileService fileService;
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

    @Autowired
    public void setDependency(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> handleFileUpload(HttpServletRequest request,
                                            @ModelAttribute ContactDto requestDto,
                                            @RequestParam(value = "contract", required = false) MultipartFile contract,
                                            @RequestParam("passport") MultipartFile passport,
                                            @RequestParam(value = "p45", required = false) MultipartFile p45,
                                            @RequestParam(value = "p60", required = false) MultipartFile p60,
                                            @RequestParam("p80") MultipartFile p80,
                                            Principal principal) {

        Order order = new Order();
        User user = null;
        if (principal != null) {
            user = userService.findByUsername(principal.getName());
            Long currenIdUser = orderService.getAll().get(orderService.getAll().size() - 1).getId() + 1;
            fileService.setDirName(String.valueOf(user.getId() + "_" + currenIdUser));
        }
        MultipartFile[] files = new MultipartFile[]{contract, passport, p45, p60, p80};
        for (MultipartFile file : files) {

            if (file != null) {
                String currentFilePath = fileService.uploadFile(file);
                if (currentFilePath == null) {
                    return new ResponseEntity<>("Failed to upload files", HttpStatus.INTERNAL_SERVER_ERROR);
                }
                order.setPathFile(order, file.getName(), currentFilePath);
            }

        }
        Order addedOrder = orderService.addOrder(order, user);
        contactService.addContact(new Contact(requestDto.getPhone(), requestDto.getEmail(), requestDto.getMessanger(),addedOrder));

        return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
    }
}
