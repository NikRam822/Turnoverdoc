package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.User;

public interface ContactService {
    Contact addContact(ContactDto contact) throws ErrorDto;
    boolean isValid(ContactDto contactDto) throws ErrorDto;
    Contact save(Contact contact);
    Contact findByEmail(String email);
    Contact linkContact(Contact contact, ContactDto contactDto);
}
