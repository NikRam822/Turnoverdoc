package com.turnoverdoc.turnover.controllers.admin;

import com.turnoverdoc.turnover.dto.DeletedAdminDto;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.model.UserStatus;
import com.turnoverdoc.turnover.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@CrossOrigin
@RestController
@Slf4j
@RequestMapping(value = "/api/v1/super-admin/auth/")
public class DeleteAdmin {

    private UserRepository userService;
    private final Logger LOGGER = log;

    @Autowired
    public void setUserService(UserRepository userService) {
        this.userService = userService;
    }
@PostMapping("remove")
    public ResponseEntity softRemoveAdmin(@RequestBody DeletedAdminDto requestDto, HttpServletResponse responseHttp, Principal principal) {
        User user = userService.findByUsername(requestDto.getLogin());
        if (user != null) {
            userService.delete(user);
        } else {
            LOGGER.error("Username is not exist: " + requestDto.getLogin());
            return  new ResponseEntity<>("User with this username is not exist", HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>("User was deleted", HttpStatus.OK);
    }
}

