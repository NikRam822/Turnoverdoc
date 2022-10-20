package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Override
    public Order addOrder(Order order) {
        return null;
    }

    @Override
    public List<Order> getAll() {
        return null;
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
