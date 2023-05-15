package com.turnoverdoc.turnover.messenger.telegram;

import com.turnoverdoc.turnover.error.types.NotFoundException;
import com.turnoverdoc.turnover.error.types.TimeoutException;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.turnoverdoc.turnover.error.ErrorsContainer.*;

@Service
@Slf4j
public class TelegramServiceImpl implements TelegramService {

    @Value("${telegram.baseurl}")
    private String baseUrl;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void sendUpdate(OrderStatus orderStatus, Order order) {
        try {
            TelegramRequest telegramRequest = TelegramRequestConverter.getMessengerRequest(orderStatus, order);
            TelegramResponse telegramResponse = restTemplate.postForObject(baseUrl, telegramRequest, TelegramResponse.class);


            // All exceptions must be non blocking!
            if (telegramResponse != null && !telegramResponse.getStatusCode().equals("0")) {
                log.error("An error occurred: " + TLG_03.getName());
            }

        } catch (TimeoutException e) {
            log.error("An error occurred: " + TLG_01.getName() + "\n" + e.getMessage());
        } catch (NotFoundException e) {
            log.error("An error occurred: " + TLG_02.getName() + "\n" + e.getMessage());
        } catch (Exception e) {
            log.error("An error occurred: " + TLG_04.getName() + "\n" + e.getMessage());
        }
    }
}
