package com.turnoverdoc.turnover.dto.authentication_request_dto;

import lombok.Data;

@Data
public class RegistrationRequest {
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String middleName;
}
