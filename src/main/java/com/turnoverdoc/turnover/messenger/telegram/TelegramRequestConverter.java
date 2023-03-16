package com.turnoverdoc.turnover.messenger.telegram;

import com.turnoverdoc.turnover.model.OrderStatus;

public class TelegramRequestConverter {
    public static TelegramRequest getMessengerRequest(OrderStatus orderStatus) {
        return TelegramRequest.builder().status(orderStatus.name()).build();
    }
}
