package com.example.LoginService.domain;

import java.io.Serializable;

public class UserData implements Serializable
{
    private  String email;
    private String  userName;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
