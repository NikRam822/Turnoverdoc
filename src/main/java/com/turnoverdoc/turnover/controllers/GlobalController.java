package com.turnoverdoc.turnover.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ResourceBundle;

@ControllerAdvice
public class GlobalController {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleError(Exception ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

}
