package com.turnoverdoc.turnover.dto;


import com.turnoverdoc.turnover.model.OrderStatus;
import lombok.Data;

@Data
public class FilterOrderDto {
    private OrderStatus statusOfOrder;
    private String username;
}
