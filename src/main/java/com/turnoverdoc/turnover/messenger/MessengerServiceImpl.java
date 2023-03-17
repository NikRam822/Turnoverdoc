package com.turnoverdoc.turnover.messenger;

import com.turnoverdoc.turnover.messenger.telegram.TelegramService;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessengerServiceImpl implements MessengerService {
    private TelegramService telegramService;

    @Autowired
    public void setTelegramService(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @Override
    public void messengerNotify(Contact contact, OrderStatus orderStatus) {
        if (contact.isMessengerNotify()) {
            switch (contact.getMessengerType()) {
                case TELEGRAM:
                    telegramService.sendUpdate(orderStatus);
                case VIBER:
                case WHATSAPP:
            }
        }
    }
}
