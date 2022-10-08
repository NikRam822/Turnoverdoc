package com.turnoverdoc.turnover.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public ResponseEntity greeting(@RequestHeader("login") String login, @RequestHeader("password") String password) {
        // code that uses the language variable
        return new ResponseEntity("your login: "+ login +"\nyour password: "+ password, HttpStatus.OK);
    }

}
