package com.codemaster.io.service;

import com.codemaster.io.models.User;
import org.springframework.beans.factory.annotation.Autowired;
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

    public boolean passwordMatch(String email, String password) {
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            return authentication.isAuthenticated();
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    public int signup(User user) {
        return userService.addUser(user);
    }

}
