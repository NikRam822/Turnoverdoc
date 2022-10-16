package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.UserDto;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/main")
public class MainController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("all")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<User> users = userService.getAll();
        List<UserDto> userDtos = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            userDtos.add(UserDto.toUserDto(users.get(i)));
        }

        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }
}
