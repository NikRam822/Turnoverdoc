package com.turnoverdoc.turnover.messenger;

import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;

public interface MessengerService {
    void messengerNotify(Order order, OrderStatus orderStatus);
}
