package com.example.LoginService.domain;

public class GoogleUser {
    private String userName;
    private String token;
    private String email;
    private boolean authType;
    private int role;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAuthType() {
        return authType;
    }

    public void setAuthType(boolean authType) {
        this.authType = authType;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }
}
