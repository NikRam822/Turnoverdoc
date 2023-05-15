package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.PasswordDto;
import com.turnoverdoc.turnover.dto.authentication_request_dto.RegistrationRequest;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.*;
import com.turnoverdoc.turnover.repositories.PasswordTokenRepository;
import com.turnoverdoc.turnover.repositories.RoleRepository;
import com.turnoverdoc.turnover.repositories.UserRepository;
import com.turnoverdoc.turnover.services.ContactService;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN_03;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordTokenRepository passwordTokenRepository;
    private PasswordEncoder passwordEncoder;

    private ContactService contactService;

    @Autowired
    public void setContactService(ContactService contactService) {
        this.contactService = contactService;
    }

    private final Logger LOGGER = log;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordTokenRepository passwordTokenRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordTokenRepository = passwordTokenRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(RegistrationRequest registrationRequest) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        User user = new User();
        user.setUsername(registrationRequest.getUsername());
        user.setFirstName(registrationRequest.getFirstName());
        user.setSecondName(registrationRequest.getSecondName());
        user.setSurname(registrationRequest.getSurname());

        user.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        user.setRoles(userRoles);
        user.setUserStatus(UserStatus.ACTIVE);

        Contact contact = new Contact();
        contact.setEmail(registrationRequest.getEmail());

        user.setContact(contactService.save(contact));

        User registeredUser = null;

        try {
            registeredUser = userRepository.save(user);
            LOGGER.info("Registered new user: {}", registeredUser);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Failed to save new user: user is null");
            throw new IllegalArgumentException(e);
        }

        return registeredUser;
    }

    public void changePassword(PasswordDto passwordDto, User user) throws ErrorDto {
        if (BCrypt.checkpw(passwordDto.getOldPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        } else {
            throw TURN_03;
        }
    }

    @Override
    public void resetPassword(String newPassword, User user) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        List<User> result = userRepository.findAll();
        return result;
    }

    @Override
    public User findByUsername(String username) {
        User result = userRepository.findByUsername(username);
        LOGGER.info("Found user - {} by username - {}", result, username);
        return result;
    }

    @Override
    public User findById(Long id) {
        User result = userRepository.findById(id).orElse(null);

        if (result == null) {
            LOGGER.warn("Not found user by id: {}", id);
        }
        LOGGER.info("Found user - {} by id - {}", result, id);
        return result;
    }

    @Override
    public void delete(Long id) {
        if (id != null) {
            userRepository.deleteById(id);
            LOGGER.info("Deleted user with id: {}", id);
        } else {
            LOGGER.warn("Id is null");
        }
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
