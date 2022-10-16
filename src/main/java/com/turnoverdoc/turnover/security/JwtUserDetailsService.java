package com.turnoverdoc.turnover.security;

import com.turnoverdoc.turnover.model.User;
import com.turnoverdoc.turnover.security.jwt.JwtUser;
import com.turnoverdoc.turnover.security.jwt.JwtUserFactory;
import com.turnoverdoc.turnover.services.UserService;
import com.turnoverdoc.turnover.services.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Autowired
    public JwtUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    private final Logger LOGGER = log;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User with username:" + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        LOGGER.info("Created user in loadUserByUsername: {}", jwtUser);
        return jwtUser;
    }
}
