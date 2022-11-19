package com.turnoverdoc.turnover.services;

import com.turnoverdoc.turnover.model.Role;

import java.util.List;

public interface AdminService {
    boolean accessAuth(List<Role> roles);
}
