package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.FilterOrderDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

public interface OrderService {

    List<Order> getAll();

    boolean saveOrderFiles(MultipartFile[] files, User user,Order order);

    Order findById(Long id);

    void delete(Long id);

    List<Order> getAllForUser(Long id);

    Order createOrder(User user, Contact contact);

    List<Order> getFilteredOrders(FilterOrderDto filterOrderDto);

    Order update(Order order);

    Order changeStatus(Order order, OrderStatus status);

    File getFileDocByOrder(Order order, String fileName);
}
