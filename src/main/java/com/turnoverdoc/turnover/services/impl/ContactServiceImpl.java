package com.turnoverdoc.turnover.services.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.ContactRepository;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN4;

@Service
@Slf4j
public class ContactServiceImpl implements ContactService {
    private final Logger LOGGER = log;
    private ContactRepository contactRepository;

    @Autowired
    public void setContactRepository(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public Contact addContact(ContactDto contactDto) throws ErrorDto {
        Contact contact = new Contact();
        contact.setPhone(contactDto.getPhone());
        contact.setMessenger(contactDto.getMessenger());

        Contact addedContact = null;

        if (isValid(contactDto)) {
            try {
                addedContact = contactRepository.save(contact);
                LOGGER.info("Added new contact: {}", addedContact);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Failed to save new contact: contact is null");
                throw new IllegalArgumentException(e);
            }
        }

        return addedContact;
    }

    @Override
    public boolean isValid(ContactDto contactDto) throws ErrorDto {
        PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
        Phonenumber.PhoneNumber userNumber = null;

        try {
            userNumber = phoneUtil.parse(contactDto.getPhone(), "GB");
        } catch (NumberParseException e) {
            LOGGER.error("Failed to parse phone number: " + e.getMessage());
            throw TURN4;
        }

        return phoneUtil.isValidNumber(userNumber) && contactDto.getMessenger() != null;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }
}
