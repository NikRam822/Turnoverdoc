package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.dto.PasswordDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.UserStatus;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.RoleRepository;
import com.turnoverdoc.turnover.repositories.UserRepository;
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

import static com.turnoverdoc.turnover.error.ErrorsContainer.TURN3;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    private final Logger LOGGER = log;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setUserStatus(UserStatus.ACTIVE);

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
            throw TURN3;
        }
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


}
