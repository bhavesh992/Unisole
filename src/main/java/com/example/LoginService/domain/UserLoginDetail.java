package com.example.LoginService.domain;

import java.io.Serializable;

public class UserLoginDetail implements Serializable {
    private String email;
    private String password;
    private int role;

    public UserLoginDetail() {
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public UserLoginDetail(String email, String password, int role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
