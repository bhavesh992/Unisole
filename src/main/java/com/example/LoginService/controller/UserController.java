package com.example.LoginService.controller;

import com.example.LoginService.domain.*;
import com.example.LoginService.entity.User;
import com.example.LoginService.exception.AuthException;
import com.example.LoginService.feignclient.CartClient;
import com.example.LoginService.services.LoginHistoryService;
import com.example.LoginService.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private LoginHistoryService loginHistoryService;

    @Autowired
    private CartClient cartClient;


    @PostMapping("/signup/{roleId}")
    public ResponseEntity signup(@RequestBody User user,@PathVariable("roleId") int roleId)
    {
        try
        {
            return new ResponseEntity<>(userService.signup(user,roleId),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(e.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity signin(@RequestBody UserLoginDetail userdetail)
    {
        try
        {
            return new ResponseEntity(userService.signin(userdetail),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(new JwtToken("Invalid Credentials"),HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/signin/google")
    public ResponseEntity signingoogle(@RequestBody GoogleUser user)
    {
        try
        {

            User googleUser= new User();
            googleUser.setUserName(user.getUserName());
            googleUser.setEmail(user.getEmail());
            googleUser.setPassword("google");
            googleUser.setToken(user.getToken());
            googleUser.setAuthType(true);
            googleUser.setSocialToken(user.getToken());
            Cart cart=new Cart();
            cart.setCartId(UUID.randomUUID().toString().substring(0, 32));
            cart.setUserId(user.getEmail());
            cartClient.createCart(cart);
            if(userService.getUserByEmail(user.getEmail())!=null)
            {
                System.out.println("in sign in");
                UserLoginDetail loginDetail=new UserLoginDetail(googleUser.getEmail(),googleUser.getPassword(),user.getRole());
                return new ResponseEntity(userService.signin(loginDetail),HttpStatus.OK);
            }
            else {
                System.out.println("in signup");
                return new ResponseEntity(userService.signup(googleUser,user.getRole()),HttpStatus.OK);
            }
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/api/{token}")
    public ResponseEntity getUserByToken(@PathVariable String token)

    {
        try
        {

            return new ResponseEntity(userService.getUser(token),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/history/{email}")
    public ResponseEntity getLoginHistory180(@PathVariable("email") String email)

    {
        try
        {
            System.out.println("called with history");
            return new ResponseEntity(loginHistoryService.getLast180DaysHistory(email),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/update")
    public ResponseEntity updateProfile(@RequestBody UserData userData)

    {
        try
        {

            return new ResponseEntity(userService.updateProfile(userData),HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/getprofile/{email}")
    public ResponseEntity getProfile(@PathVariable("email") String email)

    {
        try
        {
            User user= userService.getUserByEmail(email);

            return new ResponseEntity(user,HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity(e.getMessage().toString(),HttpStatus.BAD_REQUEST);
        }
    }

}
