package com.turnoverdoc.turnover.controllers;

import com.turnoverdoc.turnover.dto.PasswordResetDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.PasswordResetToken;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.PasswordTokenService;
import com.turnoverdoc.turnover.services.UserService;
import com.turnoverdoc.turnover.services.impl.MailSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN2;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/resetPassword")
public class PasswordResetController {
    private PasswordTokenService passwordTokenService;
    private UserService userService;
    private MailSenderService mailSenderService;
    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    @Autowired
    private void setPasswordTokenService(PasswordTokenService passwordTokenService) {
        this.passwordTokenService = passwordTokenService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @PostMapping("/requestToResetPassword")
    public ResponseEntity<String> requestToResetPassword(@RequestParam String email) {
        User user = contactService.findByEmail(email);

        if (user == null) {
            return new ResponseEntity<>("Can not find user with current email", HttpStatus.BAD_REQUEST);
        }

        PasswordResetToken passwordResetToken = passwordTokenService.createPasswordToken(user);
        mailSenderService.sendResetPassword(passwordResetToken);
        return new ResponseEntity<>("Mail for password reset successfully send", HttpStatus.OK);
    }

    @PostMapping("/resetPasswordByEmail")
    public ResponseEntity<String> resetPasswordByEmail(@RequestBody PasswordResetDto passwordResetDto) throws ErrorDto {
        PasswordResetToken passwordResetToken = passwordTokenService.findByToken(passwordResetDto.getToken());

        if (passwordResetToken != null) {
            if (!passwordResetToken.isExpired()) {
                userService.resetPassword(passwordResetDto.getNewPassword(), passwordResetToken.getUser());
                return new ResponseEntity<>("Your password successfully changed", HttpStatus.OK);
            }
            return new ResponseEntity<>("Token has expired, please request a new password reset", HttpStatus.BAD_REQUEST);
        }
        throw TURN2;
    }
}
