package com.turnoverdoc.turnover.dto;

import lombok.Data;

@Data
public class BankDetailsDto {
    private String bic;
    private String iban;
    private String orderId;
}
