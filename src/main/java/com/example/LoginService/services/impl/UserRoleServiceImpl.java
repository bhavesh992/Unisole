package com.example.LoginService.services.impl;

import com.example.LoginService.entity.UserRole;
import com.example.LoginService.repository.UserRoleRepository;
import com.example.LoginService.services.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;
    @Override
    public UserRole insertIntoUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }
}
