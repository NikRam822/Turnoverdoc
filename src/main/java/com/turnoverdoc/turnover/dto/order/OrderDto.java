package com.turnoverdoc.turnover.dto.order;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long id;
    private String passportPath;
    private String p45Path;
    private String p60Path;
    private String p80Path;
    private String contractPath;
    private OrderStatus status;
    private ContactDto contact;

    public static OrderDto toOrderDto(Order order) {
        OrderDto orderDto = new OrderDto();
        orderDto.setId(order.getId());
        orderDto.setPassportPath(order.getPassportPath());
        orderDto.setP45Path(order.getP45Path());
        orderDto.setP60Path(order.getP60Path());
        orderDto.setP80Path(order.getP80Path());
        orderDto.setContractPath(order.getContractPath());
        orderDto.setStatus(order.getStatus());
        orderDto.setContact(ContactDto.toContactDto(order.getContact()));

        return orderDto;
    }

    public static List<OrderDto> toOrderDtoList(List<Order> orders) {
        List<OrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(toOrderDto(order));
        }

        return orderDtos;
    }
}
