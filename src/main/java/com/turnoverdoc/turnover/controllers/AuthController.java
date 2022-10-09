package com.turnoverdoc.turnover.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class AuthController {

    @GetMapping("/auth")
    public ResponseEntity auth(@RequestHeader("login") String login, @RequestHeader("password") String password,HttpServletResponse response) {
        // code that uses the language variable
        Cookie cookie = new Cookie("token", login+"-"+password);//создаем объект Cookie,
        //в конструкторе указываем значения для name и value
        cookie.setPath("/");//устанавливаем путь
        cookie.setMaxAge(86400);//здесь устанавливается время жизни куки
        response.addCookie(cookie);//добавляем Cookie в запрос
        response.setContentType("text/plain");//устанавливаем контекст
        return ResponseEntity.ok().body(HttpStatus.OK);//получилось как бы два раза статус ответа установили, выбирайте какой вар
    }

}
