package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.PasswordResetToken;
import com.turnoverdoc.turnover.model.User;

public interface PasswordTokenService {
    PasswordResetToken createPasswordToken(User user);
    PasswordResetToken findByToken(String token);
}
