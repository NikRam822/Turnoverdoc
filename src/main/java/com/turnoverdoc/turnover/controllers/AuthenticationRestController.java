package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.authentication_request_dto.LoginRequestDto;
import com.turnoverdoc.turnover.dto.authentication_request_dto.RegistrationRequest;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.security.jwt.JwtTokenProvider;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/api/v1/auth")
@Slf4j
public class AuthenticationRestController {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    private final Logger LOGGER = log;

    @Autowired
    public AuthenticationRestController(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody LoginRequestDto requestDto, HttpServletResponse responseHttp) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
            User user = userService.findByUsername(username);

            if (user == null) {
                LOGGER.warn("User {} not found", username);
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(username, user.getRoles());

            Map<Object, Object> response = new HashMap<>();
            response.put("username", username);
            response.put("token", token);
            LOGGER.info("Successful login user with username {}", username);
            jwtTokenProvider.setCookieJwt(token,responseHttp);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @PostMapping("registration")
    public ResponseEntity<String> register(@RequestBody RegistrationRequest requestDto) {
        User user = userService.findByUsername(requestDto.getUsername());

        if (user != null) {
            LOGGER.warn("User with username {} is already exist", user.getUsername());
            return new ResponseEntity<>("User with this username is already exist", HttpStatus.CONFLICT);
        }

        User createdUser = userService.register(requestDto);
        LOGGER.info("Successful registration user with username {}", createdUser.getUsername());
        return new ResponseEntity<>("Successful registration", HttpStatus.CREATED);
    }

    @GetMapping("login/logout")
    public void logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }
    }
}
