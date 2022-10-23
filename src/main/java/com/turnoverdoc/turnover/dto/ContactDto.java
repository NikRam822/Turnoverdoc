package com.turnoverdoc.turnover.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContactDto {
    private String phone;
    private String email;
    private String messanger;
}
