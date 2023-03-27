package com.turnoverdoc.turnover.messenger.telegram;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;

public class TelegramRequestConverter {
    public static TelegramRequest getMessengerRequest(OrderStatus orderStatus, Order order) {
        return TelegramRequest.builder()
                .orderStatus(orderStatus.name())
                .username("nikitagru")
                .applicationId(String.valueOf(order.getId()))
                .build();
    }
}
