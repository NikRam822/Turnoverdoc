package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.BankDetailsDto;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.services.BankDetailsService;
import com.turnoverdoc.turnover.services.OrderService;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {
    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public boolean isValid(BankDetailsDto bankDetailsDto) {
        return true;
    }

    @Override
    public boolean send(BankDetailsDto bankDetailsDto, Order order) {
        order.setStatus(OrderStatus.BANK_DETAILS_RECEIVED);
        orderService.update(order);
        return true;
    }
}
