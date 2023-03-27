package com.turnoverdoc.turnover.messenger.telegram;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;

public interface TelegramService {
    void sendUpdate(OrderStatus orderStatus, Order order);
}
