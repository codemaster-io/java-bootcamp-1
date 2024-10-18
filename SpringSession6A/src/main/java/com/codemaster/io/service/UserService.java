package com.codemaster.io.service;

import com.codemaster.io.models.User;
import com.codemaster.io.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int addUser(User user) {
        int id = userRepository.getUsers().size() + 1;
        user = user.toBuilder()
                .id(id)
                .build();
        boolean success = userRepository.addUser(user);
        if(success) return id;
        return -1;
    }

    public boolean updateUser(User user) {
        userRepository.deleteUser(user.getEmail());
        userRepository.addUser(user);
        return true;
    }

    public User getUserByEmail(String email) {
        return userRepository.getUser(email);
    }

    public User getUserById(int id) {
        for(User user : userRepository.getUsers()) {
            if(user.getId() == id) return user;
        }
        return null;
    }

    public boolean deleteUserById(int id) {
        User user = getUserById(id);
        if(user != null) return userRepository.deleteUser(user.getEmail());
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.getUsers();
    }

}
