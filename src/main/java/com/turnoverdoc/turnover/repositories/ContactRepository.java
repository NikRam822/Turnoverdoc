package com.turnoverdoc.turnover.repositories;

import com.turnoverdoc.turnover.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact,Long> {
}
