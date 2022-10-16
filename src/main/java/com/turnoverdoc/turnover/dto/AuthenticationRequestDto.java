package com.turnoverdoc.turnover.dto;

import lombok.Data;


public class AuthenticationRequestDto {
    @Data
    public static class RegistrationRequest {
        private String username;
        private String password;
        private String firstName;
        private String secondName;
        private String middleName;
    }

    @Data
    public static class LoginRequestDto {
        private String username;
        private String password;
    }
}
