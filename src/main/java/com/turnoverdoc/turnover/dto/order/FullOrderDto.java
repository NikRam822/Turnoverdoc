package com.turnoverdoc.turnover.dto.order;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.model.User;
import lombok.Data;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
@Data
public class FullOrderDto {
    private Long userID;
    private String username;
    private Long id;
    private String passportPath;
    private String p45Path;
    private String p60Path;
    private String p80Path;
    private String contractPath;
    private OrderStatus status;
    private ContactDto contact;
    private Timestamp date;

    public static FullOrderDto toFullOrderDto(Order order) {
        FullOrderDto fullOrderDto = new FullOrderDto();
        if(order.getUser()!=null) {
            fullOrderDto.setUserID(order.getUser().getId());
            fullOrderDto.setUsername(order.getUser().getUsername());
        }
        fullOrderDto.setId(order.getId());
        fullOrderDto.setPassportPath(order.getPassportPath());
        fullOrderDto.setP45Path(order.getP45Path());
        fullOrderDto.setP60Path(order.getP60Path());
        fullOrderDto.setP80Path(order.getP80Path());
        fullOrderDto.setContractPath(order.getContractPath());
        fullOrderDto.setStatus(order.getStatus());
        fullOrderDto.setContact(ContactDto.toContactDto(order.getContact()));
        fullOrderDto.setDate(order.getTimestampDate());

        return fullOrderDto;
    }
    public static List<FullOrderDto> toFullOrderDtoList(List<Order> orders) {
        List<FullOrderDto> orderDtos = new ArrayList<>();

        for (Order order : orders) {
            orderDtos.add(toFullOrderDto(order));
        }

        return orderDtos;
    }
}
