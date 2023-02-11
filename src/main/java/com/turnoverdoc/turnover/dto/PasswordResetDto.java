package com.turnoverdoc.turnover.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class PasswordResetDto {
    private String token;
    private String newPassword;
}
