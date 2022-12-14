package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.PasswordDto;
import com.turnoverdoc.turnover.dto.UserDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN2;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordDto passwordDto, Principal principal) throws ErrorDto {
        User user = userService.findByUsername(principal.getName());

        if (user != null) {
            try {
                userService.changePassword(passwordDto, user);
                return new ResponseEntity<>("Your password has benn successfully updated", HttpStatus.OK);
            } catch (ErrorDto e) {
                throw e;
            }

        }

        throw TURN2;
    }
}
