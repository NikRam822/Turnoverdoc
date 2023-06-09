package com.turnoverdoc.turnover.repositories;

import com.turnoverdoc.turnover.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact,Long> {
    Contact findByEmail(String email);
    Contact findByMessenger(String messenger);
}
