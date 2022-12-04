package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ResourceBundle;

@RestControllerAdvice
public class GlobalController {
    @ExceptionHandler(ErrorDto.class)
    public ResponseEntity<String> handleError(ErrorDto ex) {
        return new ResponseEntity<>("There was an error: " + ex.getName() + " " + ex.getDescription() + " " + ex.getMessage(), HttpStatus.CONFLICT);
    }

}
