package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.PasswordResetToken;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.PasswordTokenRepository;
import com.turnoverdoc.turnover.services.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PasswordResetServiceImpl implements PasswordTokenService {
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private void setPasswordTokenRepository(PasswordTokenRepository passwordTokenRepository) {
        this.passwordTokenRepository = passwordTokenRepository;
    }


    @Override
    public PasswordResetToken createPasswordToken(User user) {
        PasswordResetToken passwordResetToken = new PasswordResetToken(user, UUID.randomUUID().toString());
        return passwordTokenRepository.save(passwordResetToken);
    }

    @Override
    public PasswordResetToken findByToken(String token) {
        return passwordTokenRepository.findByToken(token);
    }
}
