package com.example.LoginService.services.impl;

import java.util.Date;
import java.util.UUID;
import java.util.regex.Pattern;

import com.example.LoginService.Constants;
import com.example.LoginService.domain.*;
import com.example.LoginService.entity.*;
import com.example.LoginService.exception.AuthException;
import com.example.LoginService.feignclient.CartClient;
import com.example.LoginService.repository.UserRepository;
import com.example.LoginService.repository.UserRoleRepository;
import com.example.LoginService.services.LoginHistoryService;
import com.example.LoginService.services.UserRoleService;
import com.example.LoginService.services.UserService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
     private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private LoginHistoryService loginHistoryService;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private CartClient cartClient;

    @Override
    public JwtToken signin(UserLoginDetail userLoginDetail) throws AuthException {

        try {
            int role=userLoginDetail.getRole();
            UserRole userRole=userRoleRepository.findById(new UserRolePK(userRepository.findByEmail(userLoginDetail.getEmail()),role)).get();
            if(userRole==null){
                throw new AuthException("User Role Invalid");
            }
            User check = userRepository.findByEmail(userLoginDetail.getEmail());
            if (!BCrypt.checkpw(userLoginDetail.getPassword(), check.getPassword())) {
		        throw new AuthException("Invalid email/password");
            }
		    userRepository.updateTokenByEmail(check.getEmail(), generateJwtToken(check).getToken());
            check.setToken(userRepository.getTokenByEmail(userLoginDetail.getEmail()));
            LoginHistory loginHistory=new LoginHistory();
            loginHistory.setDate(new Date());
            loginHistory.setEmail(check.getEmail());
            loginHistory.setLoginId(UUID.randomUUID().toString().substring(0, 32));
            loginHistoryService.logHistory(loginHistory);
		    return new JwtToken(check.getToken());
        } catch (Exception e) {
            throw new AuthException("Invalid email/password="+e.getMessage());
        }
    }

    @Override
    public JwtToken signup(User user,int roleId) throws AuthException {

        Pattern pattern = Pattern.compile("^(.+)@(.+)$");
        String email = user.getEmail();
        if (!pattern.matcher(email).matches()) {
            throw new AuthException("Invalid Email");
        }
        User checkIfExists = userRepository.findByEmail(user.getEmail());
        if (checkIfExists != null) {
            throw new AuthException("User Already Exists");
        } else {

            try {
//                System.out.println("user email="+email+" "+user.getPassword()+" "+user.getLoginType());
                User u = new User();
                u.setEmail(user.getEmail());
                String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));
                u.setPassword(hashedPassword);
                if(!user.getPassword().equals("google"))u.setLoginType("normal");
                else u.setLoginType("google");
                u.setUserName(user.getUserName());
                if(user.getPassword().equals("google")) {
                    u.setSocialToken(generateJwtToken(u).getToken());
                    u.setAuthType(true);
                }
                else {u.setToken(generateJwtToken(u).getToken());u.setAuthType(false);}
                LoginHistory loginHistory=new LoginHistory();
                loginHistory.setDate(new Date());
                loginHistory.setEmail(u.getEmail());
                loginHistory.setLoginId(UUID.randomUUID().toString().substring(0, 32));
                loginHistoryService.logHistory(loginHistory);
                userRepository.save(u);
                Cart cart=new Cart();
                cart.setCartId(UUID.randomUUID().toString().substring(0, 32));
                cart.setUserId(user.getEmail());
                cartClient.createCart(cart);
                userRoleService.insertIntoUserRole(new UserRole(new UserRolePK(user,roleId)));
                return new JwtToken(u.getToken());
            } catch (Exception e) {
                System.out.println("error in sign up="+e.getMessage());
                throw new AuthException("InValidDetails.Failed To Create Account=" + e.getMessage());
            }
        }
    }
    @Override
    public UserEmail getUser(String token) {
        UserEmail ue=new UserEmail();
        System.out.println(token);
        ue.setEmail(userRepository.getEmailByToken(token));
        System.out.println(ue.getEmail());
        return ue;
    }

    @Override
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public JwtToken generateJwtToken(User user) {
        long timestamp = System.currentTimeMillis();
        String token = Jwts.builder().signWith(SignatureAlgorithm.HS256, Constants.API_SECRET_KEY)
                .setIssuedAt(new Date(timestamp))
                .setExpiration(new Date(timestamp + Constants.TOKEN_VALIDITY))
                .claim("email", user.getEmail())
                .compact();
        JwtToken jwt = new JwtToken();
        jwt.setToken(token);
        return jwt;
    }



    @Override
    public boolean checkIfTokenExits(String token) {
        try {
            String check = userRepository.getEmailByToken(token);
            if (check.length() > 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.out.println("Exception in checktoken=" + e.getMessage());
            return false;
        }
    }

    @Override
    public User updateProfile(UserData userData) {
        try
        {
            userRepository.updateProfile(userData.getEmail(),userData.getUserName());
            return userRepository.findByEmail(userData.getEmail());
        }
        catch (Exception e)
        {
            return userRepository.findByEmail(userData.getEmail());
        }
    }

}