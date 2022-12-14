package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.PasswordDto;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.User;

import java.util.List;

public interface UserService {
    User register(User user);
    List<User> getAll();
    User findByUsername(String login);
    User findById(Long id);
    void delete(Long id);
    void changePassword(PasswordDto passwordDto, User user) throws ErrorDto;
}
