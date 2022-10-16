package com.turnoverdoc.turnover.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
    private String username;
    private String password;
    private String firstName;
    private String secondName;
    private String middleName;

}
