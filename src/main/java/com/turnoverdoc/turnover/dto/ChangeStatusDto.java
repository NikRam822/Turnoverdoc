package com.turnoverdoc.turnover.dto;

import lombok.Data;

@Data
public class ChangeStatusDto {
    private String orderId;
    private String status;
}
