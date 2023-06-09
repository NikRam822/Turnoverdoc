package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.Contact;
import com.turnoverdoc.turnover.model.MessengerType;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContactDto {
    private String phone;
    private String messenger;
    private String email;
    private boolean isNotify;
    private MessengerType messengerType;
    private boolean isPersonal;

    public static ContactDto toContactDto(Contact contact) {
        if (contact != null) {
            ContactDto contactDto = new ContactDto();
            contactDto.setMessenger(contact.getMessenger());
            contactDto.setPhone(contact.getPhone());
            contactDto.setEmail(contact.getEmail());
            contactDto.setNotify(contact.isMessengerNotify());
            contactDto.setPersonal(contact.isPersonal());
            contactDto.setMessengerType(contact.getMessengerType());
            return contactDto;
        }
       return null;
    }

    public static Contact toContact(ContactDto contactDto) {
        if (contactDto != null) {
            Contact contact = new Contact();
            contact.setMessenger(contactDto.getMessenger());
            contact.setPhone(contactDto.getPhone());
            contact.setEmail(contactDto.getEmail());
            contact.setMessengerNotify(contactDto.isNotify());
            contact.setPersonal(contactDto.isPersonal());
            contact.setMessengerType(contactDto.getMessengerType());
            return contact;
        }
        return null;
    }
}
