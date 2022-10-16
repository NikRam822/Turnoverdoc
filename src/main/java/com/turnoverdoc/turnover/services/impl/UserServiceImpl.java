package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.Status;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.repositories.RoleRepository;
import com.turnoverdoc.turnover.repositories.UserRepository;
import com.turnoverdoc.turnover.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final Logger LOGGER = log;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public User register(User user) {
        Role roleUser = roleRepository.findByName("ROLE_USER");
        List<Role> userRoles = new ArrayList<>();
        userRoles.add(roleUser);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(userRoles);
        user.setStatus(Status.ACTIVE);

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
