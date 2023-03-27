package com.turnoverdoc.turnover.messenger.telegram;

import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TelegramServiceImpl implements TelegramService {

    @Value("${telegram.baseurl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendUpdate(OrderStatus orderStatus, Order order) {
        TelegramRequest telegramRequest = TelegramRequestConverter.getMessengerRequest(orderStatus, order);
        TelegramResponse telegramResponse = restTemplate.postForObject(baseUrl, telegramRequest, TelegramResponse.class);
    }
}
