package com.codemaster.io.repository;

import com.codemaster.io.models.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {
    private Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
    }

    public boolean addUser(User user) {
        if(userMap.containsKey(user.getEmail())) return false;
        userMap.put(user.getEmail(), user);
        return true;
    }

    public boolean passwordMatch(String email, String password) {
        if(!userMap.containsKey(email)) return false;
        if(userMap.get(email).getPassword().equals(password)) return true;
        return false;
    }

    public List<User> getUsers() {
        return (new ArrayList<>(userMap.values()));
    }

    public User getUser(String email) {
        return userMap.get(email);
    }

    public boolean deleteUser(String email) {
        if(userMap.containsKey(email)) {
            userMap.remove(email);
            return true;
        }
        return false;
    }
}
