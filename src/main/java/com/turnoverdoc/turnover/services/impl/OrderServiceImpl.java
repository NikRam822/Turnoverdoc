package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.OrderRepository;
import com.turnoverdoc.turnover.services.FileService;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private FileService fileService;

    private final OrderRepository orderRepository;

    private final Logger LOGGER = log;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public void setDependency(FileService fileService) {
        this.fileService = fileService;
    }

    @Override
    public Order addOrder(Order order, User user) {
        order.setContractPath(order.getContractPath());
        order.setP45Path(order.getP45Path());
        order.setP60Path(order.getP60Path());
        order.setP80Path(order.getP80Path());
        order.setPassportPath(order.getPassportPath());
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
    public void updateOrder(Order order) {
        Optional<Order> orderFromDb = orderRepository.findById(order.getId());
        Order currentOrder = orderFromDb.get();

        currentOrder.setContractPath(order.getContractPath());
        currentOrder.setP45Path(order.getP45Path());
        currentOrder.setP60Path(order.getP60Path());
        currentOrder.setP80Path(order.getP80Path());

        try {
            Order savedOrder = orderRepository.save(currentOrder);
            LOGGER.info("Update order: {}", savedOrder);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to save order: order is null");
            throw new IllegalArgumentException(e);
        }

    }

    @Override
    public boolean saveOrderFiles(MultipartFile[] files, User user, Order order) {
        fileService.setDirPath(String.valueOf(user.getId() + "_" + order.getId()));

        if (fileService.saveFiles(files, order)) {
            updateOrder(order);
            return true;
        } else {
            return false;
        }

    }

    @Override
    public List<Order> getAll() {
        List<Order> result = orderRepository.findAll();
        return result;
    }

    @Override
    public List<Order> getAllForUser(Long id) {
        return orderRepository.findByUserId(id);
    }

    @Override
    public Order findById(Long id) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }


}
