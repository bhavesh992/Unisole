package com.example.LoginService.entity;


import java.io.Serializable;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



/**
 * The persistent class for the user database table.
 *
 */
@Entity
@Table(name="users")
public class User implements Serializable {
    @Id
    private String email;

    private String password;

    private String token;
    private String userName;

    private String socialToken;

    public String getSocialToken() {
        return socialToken;
    }

    public void setSocialToken(String socialToken) {
        this.socialToken = socialToken;
    }

    public Boolean getAuthType() {
        return authType;
    }

    public void setAuthType(Boolean authType) {
        this.authType = authType;
    }

    private Boolean authType; // is this local or social

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    private String loginType;


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public User() {
    }



    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return this.token;
    }

    public void setToken(String token) {
        this.token = token;
    }





}