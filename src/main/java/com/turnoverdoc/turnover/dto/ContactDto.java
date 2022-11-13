package com.turnoverdoc.turnover.dto;

import com.turnoverdoc.turnover.model.Contact;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ContactDto {
    private String phone;
    private String email;
    private String messenger;

    public static ContactDto toContactDto(Contact contact) {
        if (contact != null) {
            ContactDto contactDto = new ContactDto();
            contactDto.setEmail(contact.getEmail());
            contactDto.setMessenger(contact.getMessenger());
            contactDto.setPhone(contact.getPhone());

            return contactDto;
        }
       return null;
    }
}
