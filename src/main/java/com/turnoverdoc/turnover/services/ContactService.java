package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.ContactDto;
import com.turnoverdoc.turnover.model.Contact;

public interface ContactService {
    Contact addContact(ContactDto contact);
    boolean isValid(ContactDto contactDto);
}
