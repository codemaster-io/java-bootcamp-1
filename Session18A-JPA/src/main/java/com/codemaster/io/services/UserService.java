package com.codemaster.io.services;

import com.codemaster.io.models.User;
import com.codemaster.io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    // Find users by name
    public List<User> findUsersByName(String name) {
        return userRepository.findByName(name);
    }

    // Find users with orders
//    public List<User> findUsersWithOrders() {
//        return userRepository.findUsersWithOrders();
//    }

    // Save a user
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findUser(Integer id) {
        return userRepository.findById(id);
    }
}

