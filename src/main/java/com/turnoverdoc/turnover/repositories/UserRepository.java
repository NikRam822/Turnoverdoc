package com.turnoverdoc.turnover.repositories;

import com.turnoverdoc.turnover.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query(value = "SELECT * FROM users WHERE contact_id=(SELECT contacts.id FROM contacts WHERE contacts.email=:email AND contacts.is_personal=true)", nativeQuery = true)
    @Transactional
    User findByEmail(@Param("email") String email);
}
