package com.turnoverdoc.turnover.services.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;
import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.repositories.ContactRepository;
import com.turnoverdoc.turnover.services.ContactService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN_04;
import static com.turnoverdoc.turnover.error.ErrorsContainer.VAL_02;

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
        contact.setMessenger(contactDto.getMessenger().replace("@", ""));
        contact.setPersonal(contactDto.isPersonal());
        contact.setMessengerNotify(contactDto.isNotify());
        contact.setMessengerType(contactDto.getMessengerType());
        //TODO: Create email validation
        contact.setEmail(contactDto.getEmail());

        Contact addedContact = null;

        // Check if there is a contact in the database with the current username that is linked to the profile
        if (contactDto.isPersonal() && contactDto.getMessenger() != null) {
            Contact contactFromDb = contactRepository.findByMessenger(contactDto.getMessenger());
            if (contactFromDb != null && contactFromDb.isPersonal()) {
                // TODO: Replace to new validation error
                throw VAL_02;
            }
        }

        if (isValid(contactDto)) {
            try {
                addedContact = contactRepository.save(contact);
                LOGGER.info("Added new contact: {}", addedContact);
            } catch (IllegalArgumentException e) {
                LOGGER.error("Failed to save new contact: contact is null");
                // TODO: Replace to DB error
                throw VAL_02;
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
            throw VAL_02;
        }

        return phoneUtil.isValidNumber(userNumber) && contactDto.getMessenger() != null;
    }

    @Override
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact findByEmail(String email) {
        return contactRepository.findByEmail(email);
    }

    @Override
    public Contact linkContact(Contact contact, ContactDto contactDto) {
        // Check if there is a contact in the database with the current username that is linked to the profile
        if (contactDto.isPersonal() && contactDto.getMessenger() != null) {
            Contact contactFromDb = contactRepository.findByMessenger(contactDto.getMessenger());
            if (contactFromDb != null && contactFromDb.isPersonal()) {
                // TODO: Replace to new validation error
                throw VAL_02;
            }
        }

        contact.setEmail(contactDto.getEmail());
        contact.setPhone(contactDto.getPhone());
        contact.setMessenger(contactDto.getMessenger());
        contact.setMessengerType(contactDto.getMessengerType());

        return contactRepository.save(contact);
    }
}
