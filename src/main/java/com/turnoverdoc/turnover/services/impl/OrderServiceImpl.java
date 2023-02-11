package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.FilterOrderDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.ContactRepository;
import com.turnoverdoc.turnover.repositories.OrderRepository;
import com.turnoverdoc.turnover.services.FileService;
import com.turnoverdoc.turnover.services.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    private FileService fileService;
    private MailSenderService mailSenderService;

    private final OrderRepository orderRepository;
    private ContactRepository contactRepository;

    private final Logger LOGGER = log;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

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
        order.setStatus(OrderStatus.CONTACT_RECEIVED);
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
    public boolean saveOrderFiles(MultipartFile[] files, User user, Order order) {

        if (fileService.saveFiles(files, order, String.valueOf(user.getId() + "_" + order.getId()))) {
            update(order);
            return true;
        }
        return false;

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
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public Order createOrder(User user, Contact contact) {
        Order order = new Order();
        order.setUser(user);
        order.setContact(contact);
        order.setStatus(OrderStatus.CONTACT_RECEIVED);
        order.setTimestampDate(new Timestamp(new Date().getTime()));
        Order savedOrder = orderRepository.save(order);

        mailSenderService.sendChangeStatusEmail(order.getUser().getEmail(), OrderStatus.CONTACT_RECEIVED.getMailDescription());

        return savedOrder;
    }

    @Override
    public List<Order> getFilteredOrders(FilterOrderDto filterOrderDto) {
        Order example = new Order();
        example.setStatus(filterOrderDto.getStatusOfOrder());
        if (filterOrderDto.getUsername() != null) {
            User userExample = new User();
            userExample.setUsername(filterOrderDto.getUsername());
            example.setUser(userExample);
        }

        List<Order> orders = orderRepository.findAll(Example.of(example));
        return orders;
    }

    @Override
    public Order update(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order changeStatus(Order order, OrderStatus status) {
        order.setStatus(status);
        Order updatedOrder = update(order);
        mailSenderService.sendChangeStatusEmail(order.getUser().getEmail(), status.getMailDescription());
        return updatedOrder;
    }

    @Override
    public File getFileDocByOrder(Order order, String fileName) {
        return fileService.getFileDoc(order.getUser().getId() + "_" + order.getId(),fileName);
    }
}
