package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.dto.PasswordDto;
import com.turnoverdoc.turnover.dto.authentication_request_dto.RegistrationRequest;
import com.turnoverdoc.turnover.error.ErrorDto;
import com.turnoverdoc.turnover.model.User;

import java.util.List;

public interface UserService {
    User register(RegistrationRequest registrationRequest);
    List<User> getAll();
    User findByUsername(String login);
    User findById(Long id);
    void delete(Long id);
    void changePassword(PasswordDto passwordDto, User user) throws ErrorDto;
    User findByEmail(String email);
    void resetPassword(String newPassword, User user);
}
