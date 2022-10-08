package com.turnoverdoc.turnover.controllers;

import org.springframework.web.bind.annotation.*;
import org.testng.annotations.Test;

@RestController
public class TestController {


    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
    @RequestMapping(value = "/p" , method = RequestMethod.POST)

    public @ResponseBody
    Test addNewWorker(@RequestBody Test jsonString) {

        //do business logic
        return jsonString;
    }

}
