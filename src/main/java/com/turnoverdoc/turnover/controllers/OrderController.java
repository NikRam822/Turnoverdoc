package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.services.FileService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final Logger LOGGER = log;
    FileService fileService;

    @Autowired
    public void setDependency(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<String> handleFileUpload(@RequestParam("contract") MultipartFile contract,
                                            @RequestParam("passport") MultipartFile passport,
                                            @RequestParam("p45") MultipartFile p45,
                                            @RequestParam("p60") MultipartFile p60,
                                            @RequestParam("p80") MultipartFile p80) {
        MultipartFile[] files = new MultipartFile[]{contract, passport, p45, p60, p80};
        for (MultipartFile file : files) {
            if (!fileService.uploadFile(file)) {
                return new ResponseEntity<>("Failed to upload files", HttpStatus.INTERNAL_SERVER_ERROR);
            }

        }
        return new ResponseEntity<>("Files successfully uploaded", HttpStatus.OK);
    }
}
