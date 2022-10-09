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
import org.springframework.stereotype.Service;

@Component
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {
    private UserServiceImpl userService;
    private final Logger LOGGER = log;


    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userService.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User with login:" + login + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        LOGGER.info("Created user in loadUserByUsername: {}", jwtUser);
        return jwtUser;
    }
}
