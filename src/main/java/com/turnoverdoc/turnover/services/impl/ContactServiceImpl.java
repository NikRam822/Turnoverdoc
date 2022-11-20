package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.repositories.ContactRepository;
import com.turnoverdoc.turnover.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public Contact addContact(ContactDto contactDto) {
        Contact contact = new Contact();
        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setMessenger(contactDto.getMessenger());

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
