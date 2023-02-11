package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.Contact;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContactDto {
    private String phone;
    private String messenger;

    public static ContactDto toContactDto(Contact contact) {
        if (contact != null) {
            ContactDto contactDto = new ContactDto();
            contactDto.setMessenger(contact.getMessenger());
            contactDto.setPhone(contact.getPhone());

            return contactDto;
        }
       return null;
    }

    public static Contact toContact(ContactDto contactDto) {
        if (contactDto != null) {
            Contact contact = new Contact();
            contact.setMessenger(contactDto.getMessenger());
            contact.setPhone(contactDto.getPhone());
            return contact;
        }
        return null;
    }
}
