package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.model.UserStatus;
import com.turnoverdoc.turnover.repositories.RoleRepository;
import com.turnoverdoc.turnover.repositories.UserRepository;
import com.turnoverdoc.turnover.services.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

private final Logger LOGGER = log;
    @Autowired
    public AdminServiceImpl(RoleRepository roleRepository, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public boolean accessAuth(List<Role> roles) {
        boolean accessAuth = false;
        for (Role userRole : roles) {

            if ((userRole.getName().equals("ROLE_ADMIN") || userRole.getName().equals("ROLE_SUPER_ADMIN"))) {
                accessAuth = true;
                break;
            }
        }
        return accessAuth;
    }

    @Override
    public User registration(User user) {
        Role roleUser = roleRepository.findByName("ROLE_ADMIN");
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
}
