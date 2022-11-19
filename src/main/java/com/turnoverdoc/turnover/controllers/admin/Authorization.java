package com.turnoverdoc.turnover.controllers.admin;

import com.turnoverdoc.turnover.dto.authentication_request_dto.LoginRequestDto;
import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.security.jwt.JwtTokenProvider;
import com.turnoverdoc.turnover.services.AdminService;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class Authorization {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;
    private final AdminService adminService;
    private final Logger LOGGER = log;

    @Autowired
    public Authorization(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService, AdminService adminService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
        this.adminService = adminService;
    }


    @PostMapping("/api/v1/auth/admin/login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto, HttpServletResponse responseHttp) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                LOGGER.warn("User {} not found", username);
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            } else {
                boolean accessAuth = adminService.accessAuth(user.getRoles());
                if (!accessAuth) {
                    LOGGER.warn("Access denied for {}", username);
                    return ResponseEntity.badRequest().body("Access denied for " + username);
                }
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            LOGGER.info("Successful login user with username {}", username);
            jwtTokenProvider.setCookieJwt(token, responseHttp);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
