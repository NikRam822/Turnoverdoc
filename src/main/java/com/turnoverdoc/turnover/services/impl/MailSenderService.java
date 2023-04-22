package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailSenderService {
    @Autowired
    private JavaMailSender emailSender;

    private final String RESET_PASSWORD_URL = "ссылка_для_восстановления_пароля_";

    @Value("${spring.mail.username}")
    private String mailAddresFrom;

    public void sendChangeStatusEmail(String toAddress, String message) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailAddresFrom);
        simpleMailMessage.setTo(toAddress);
        simpleMailMessage.setSubject("Изменение статуса заявки");
        simpleMailMessage.setText(message);
        emailSender.send(simpleMailMessage);
    }

    public void sendResetPassword(PasswordResetToken passwordResetToken) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom(mailAddresFrom);
        simpleMailMessage.setTo(passwordResetToken.getUser().getContact().getEmail());
        simpleMailMessage.setSubject("Восстановление пароля");
        simpleMailMessage.setText(RESET_PASSWORD_URL + passwordResetToken.getToken());
        emailSender.send(simpleMailMessage);
    }
}
