package com.turnoverdoc.turnover.messenger;

import com.turnoverdoc.turnover.messenger.telegram.TelegramService;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Order;
import com.turnoverdoc.turnover.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.turnoverdoc.turnover.model.MessengerType.*;

@Service
public class MessengerServiceImpl implements MessengerService {
    private TelegramService telegramService;

    @Autowired
    public void setTelegramService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void messengerNotify(Order order, OrderStatus orderStatus) {
        if (order.getContact().isMessengerNotify()) {
            switch (order.getContact().getMessengerType()) {
                case TELEGRAM:
                    telegramService.sendUpdate(orderStatus, order);
                case VIBER:
                case WHATSAPP:
            }
        }
    }
}
