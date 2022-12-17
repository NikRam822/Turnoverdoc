package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.error.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorWrapperController {
    @ExceptionHandler(ErrorDto.class)
    public ResponseEntity<String> handleError(ErrorDto ex) {
        return new ResponseEntity<>("There was an error: " + ex.getDescription(), HttpStatus.CONFLICT);
    }

}
