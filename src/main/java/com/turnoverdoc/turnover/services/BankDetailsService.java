package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.BankDetailsDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Order;
import org.aspectj.weaver.ast.Or;

public interface BankDetailsService {

    // Create transaction or send user's bank details to admin
    boolean send(BankDetailsDto bankDetailsDto, Order order) throws ErrorDto;

    boolean isValid(BankDetailsDto bankDetailsDto) throws ErrorDto;
}
