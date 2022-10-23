package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.model.UserStatus;
import com.turnoverdoc.turnover.repositories.ContactRepository;
import com.turnoverdoc.turnover.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final Logger LOGGER = log;
    ContactRepository contactRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }


    @Override
    public Contact addContact(Contact contact) {
        contact.setEmail(contact.getEmail());
        contact.setPhone(contact.getPhone());
        contact.setMessanger(contact.getMessanger());
        contact.setOrder(contact.getOrder());

        Contact addedContact = null;

        try {
            addedContact = contactRepository.save(contact);
            LOGGER.info("Added new contact: {}", addedContact);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to save new contact: user is null");
            throw new IllegalArgumentException(e);
        }

        return addedContact;
    }


}
