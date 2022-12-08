package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.OrderStatus;
import lombok.Data;

@Data
public class ChangeStatusDto {
    private String orderId;
    private OrderStatus status;
}
