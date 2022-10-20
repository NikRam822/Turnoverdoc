package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.User;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order);
    List<Order> getAll();
    Order findById(Long id);
    void delete(Long id);

}
