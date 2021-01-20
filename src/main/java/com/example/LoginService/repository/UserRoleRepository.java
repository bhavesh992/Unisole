package com.example.LoginService.repository;

import com.example.LoginService.entity.UserRole;
import com.example.LoginService.entity.UserRolePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRole,UserRolePK> {


}
