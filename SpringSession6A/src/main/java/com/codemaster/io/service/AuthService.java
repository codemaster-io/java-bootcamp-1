package com.codemaster.io.service;

import com.codemaster.io.models.User;
import org.springframework.stereotype.Component;

@Component
public class AuthService {
    private UserService userService;

    private AuthService(UserService userService) {
        this.userService = userService;
    }

    public User signIn(String email, String password) {
        User user = userService.getUserByEmail(email);
        if(user == null) return null;
        if(user.getPassword().equals(password)) return user;
        return null;
    }

    public int signup(User user) {
        return userService.addUser(user);
    }

}
