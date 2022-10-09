package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.security.jwt.JwtTokenProvider;
import com.turnoverdoc.turnover.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestController
public class AuthController {


    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/api/v1/auth/login")
    public ResponseEntity auth(@RequestHeader("login") String login, @RequestHeader("password") String password,HttpServletResponse response) {

        //JwtTokenProvider jwtTokenProvider=new JwtTokenProvider();

        String token = jwtTokenProvider.createToken(login, Collections.singletonList(new Role(454L,"ROLE_ADMIN",null)));
        // code that uses the language variable
        Cookie cookie = new Cookie("token", token);//создаем объект Cookie,
        //в конструкторе указываем значения для name и value
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вар
    }

}
