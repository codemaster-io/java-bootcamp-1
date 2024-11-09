package com.codemaster.io.services;

import com.codemaster.io.models.User;
import com.codemaster.io.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int addUserAndReturnId(User user) {
        return userRepository.addUserAndReturnId(user);
    }

    public boolean addUser(User user) {
        return userRepository.addUser(user);
    }

    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public List<User> getListUser() {
        return userRepository.getAllUsers();
    }

    @Transactional
    public boolean addUserAndAuditData(User user) {
        boolean chk = userRepository.addUser(user);
        System.out.println("Data Inserted = " + chk);
        if(chk) return userRepository.addAuditData(user.getName());
        return false;
    }
}
