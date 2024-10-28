package com.codemaster.io.service;

import com.codemaster.io.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    private AuthService(UserService userService) {
        this.userService = userService;
    }

    public Authentication authenticate(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));;
       return authentication;
    }

    public long signup(User user) {
        return userService.addUser(user);
    }

}
