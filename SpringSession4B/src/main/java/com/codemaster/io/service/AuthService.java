package com.codemaster.io.service;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.models.User;
import com.codemaster.io.repository.UserRepository;

import java.util.List;

public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public String register(User user) {
        user = user.toBuilder()
                .username(user.getName().toLowerCase())
                .build();

        boolean success = userRepository.register(user);
        if(success) return user.getUsername();
        return "";
    }

    public User signIn(String username, String password) {
        if(userRepository.passwordMatch(username, password)) return userRepository.getUser(username);
        return User.builder().build();
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }
}
