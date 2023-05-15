package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.BankDetailsDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import com.turnoverdoc.turnover.services.BankDetailsService;
import com.turnoverdoc.turnover.services.OrderService;
import org.iban4j.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN_04;
import static com.turnoverdoc.turnover.error.ErrorsContainer.VAL_01;

@Service
public class BankDetailsServiceImpl implements BankDetailsService {
    private OrderService orderService;
    private MailSenderService mailSenderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setMailSenderService(MailSenderService mailSenderService) {
        this.mailSenderService = mailSenderService;
    }

    @Override
    public boolean send(BankDetailsDto bankDetailsDto, Order order) throws ErrorDto {
        if (isValid(bankDetailsDto)) {
            orderService.changeStatus(order, OrderStatus.BANK_DETAILS_RECEIVED);
            return true;
        }
        return false;
    }

    @Override
    public boolean isValid(BankDetailsDto bankDetailsDto) throws ErrorDto {
        try {
            IbanUtil.validate(bankDetailsDto.getIban());
            BicUtil.validate(bankDetailsDto.getBic());
        } catch (IbanFormatException |
                InvalidCheckDigitException |
                UnsupportedCountryException | BicFormatException e) {
            // if the input data is null or in the wrong format
            throw VAL_01;
        }

        return true;
    }
}
