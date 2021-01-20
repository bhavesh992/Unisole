package com.example.LoginService.services;

import com.example.LoginService.domain.JwtToken;
import com.example.LoginService.domain.UserData;
import com.example.LoginService.domain.UserEmail;
import com.example.LoginService.domain.UserLoginDetail;
import com.example.LoginService.entity.User;
import com.example.LoginService.exception.AuthException;

public interface UserService {
    JwtToken signin(UserLoginDetail user) throws AuthException;
    JwtToken signup(User user,int roleId) throws AuthException;
//    String changePassword(User user) throws AuthException;
    JwtToken generateJwtToken(User user) throws AuthException;
    UserEmail getUser(String email);
    User getUserByEmail(String email);
    boolean checkIfTokenExits(String token);
    User updateProfile(UserData userData);
}
