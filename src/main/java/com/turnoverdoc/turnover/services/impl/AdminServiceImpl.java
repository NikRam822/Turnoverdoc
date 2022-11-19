package com.turnoverdoc.turnover.services.impl;

import com.turnoverdoc.turnover.model.Role;
import com.turnoverdoc.turnover.services.AdminService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AdminServiceImpl implements AdminService {
    @Override
    public boolean accessAuth(List<Role> roles) {
        boolean accessAuth=false;
        for (Role userRole : roles) {

            if ((userRole.getName().equals("ROLE_ADMIN") || userRole.getName().equals("ROLE_SUPER_ADMIN"))) {
                accessAuth = true;
                break;
            }
        }
        return accessAuth;
    }
}
