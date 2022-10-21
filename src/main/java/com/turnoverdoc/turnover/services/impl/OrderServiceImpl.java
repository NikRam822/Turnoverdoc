package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.OrderRepository;
import com.turnoverdoc.turnover.repositories.RoleRepository;
import com.turnoverdoc.turnover.repositories.UserRepository;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final Logger LOGGER = log;


    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order addOrder(Order order, User user) {
        order.setContractPath("test/cont");
        order.setP45Path("test/p45");
        order.setP60Path("test/p60");
        order.setP80Path("test/p80");
        order.setPassportPath("test/pass");
        order.setStatus(OrderStatus.RECEIVED);
        order.setUser(user);
        Order addedOrder = null;
        try {
            addedOrder = orderRepository.save(order);
            LOGGER.info("Added new order: {}", addedOrder);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to save new order: order is null");
            throw new IllegalArgumentException(e);
        }
        return addedOrder;
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
