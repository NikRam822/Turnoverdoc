package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.model.User;

import java.util.List;

public interface AdminService {
    boolean accessAuth(List<Role> roles);
    User registration (User user);
}
