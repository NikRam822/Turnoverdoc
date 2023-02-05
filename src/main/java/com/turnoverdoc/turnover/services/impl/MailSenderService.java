package com.turnoverdoc.turnover.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    @Value("${spring.mail.username}")
    private String username;

    public void sendChangeStatusEmail(String toAddress, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject("Изменение статуса заявки");
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }
}
