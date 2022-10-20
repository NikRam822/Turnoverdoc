package com.turnoverdoc.turnover.dto.authentication_request_dto;

import lombok.Data;

@Data
public class LoginRequestDto {
    private String username;
    private String password;
}

