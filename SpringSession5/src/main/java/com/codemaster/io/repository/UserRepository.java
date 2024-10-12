package com.codemaster.io.repository;

import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository {
    private Map<String, User> userMap;

    public UserRepository() {
        userMap = new HashMap<>();
    }

    public boolean register(User user) {
        if(userMap.containsKey(user.getUsername())) return false;
        userMap.put(user.getUsername(), user);
        return true;
    }

    public boolean passwordMatch(String username, String password) {
        if(!userMap.containsKey(username)) return false;
        if(userMap.get(username).getPassword().equals(password)) return true;
        return false;
    }

    public List<User> getUsers() {
        return (new ArrayList<>(userMap.values()));
    }

    public User getUser(String username) {
        return userMap.get(username);
    }

    public boolean deleteUser(String username) {
        if(userMap.containsKey(username)) {
            userMap.remove(username);
            return true;
        }
        return false;
    }
}
