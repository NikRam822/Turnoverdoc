package com.turnoverdoc.turnover.dto.order;

import lombok.Data;

@Data
public class ConfirmDocsDto {
    private String orderId;
    private boolean messengerNotify;
}
