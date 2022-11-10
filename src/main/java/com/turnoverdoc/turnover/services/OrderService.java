package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface OrderService {
    Order addOrder(Order order, User user);

    List<Order> getAll();

    void updateOrder(Order order);

    boolean saveOrderFiles(MultipartFile[] files, User user,Order order);

    Order findById(Long id);

    void delete(Long id);

    List<Order> getAllForUser(Long id);

}
