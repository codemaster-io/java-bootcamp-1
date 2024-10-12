package com.codemaster.io.service;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.models.User;
import com.codemaster.io.repository.UserRepository;

import java.util.List;

@Component
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    public User register(User user) {
        boolean success = userRepository.register(user);
        if(success) return user;
        return User.builder().build();
    }

    public User signIn(String username, String password) {
        if(userRepository.passwordMatch(username, password)) return userRepository.getUser(username);
        return null;
    }

    public User getUser(String username) {
        return userRepository.getUser(username);
    }

    public boolean deleteUser(String username) {
        return userRepository.deleteUser(username);
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }
}
