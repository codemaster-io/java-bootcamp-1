package com.codemaster.io.controller;

import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.DeleteResponse;
import com.codemaster.io.models.dto.UsersListResponse;
import com.codemaster.io.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable int userId) {
        User user = userService.getUserById(userId);
        return user;
    }

    @GetMapping("/")
    public UsersListResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        UsersListResponse response = UsersListResponse.builder()
                .users(users)
                .build();
        return response;
    }

    @PostMapping("/")
    public User addUser(@RequestBody User user) {
        int id = userService.addUser(user);
        if(id == -1) return userService.getUserById(id);
        return null;
    }

    @PutMapping("/")
    public User updateUser(@RequestBody User user) {
        boolean success = userService.updateUser(user);
        if(success) return userService.getUserById(user.getId());
        return null;
    }

    @DeleteMapping("/{userId}")
    public DeleteResponse deleteUser(@PathVariable int userId) {
        boolean success = userService.deleteUserById(userId);
        DeleteResponse response = DeleteResponse.builder()
                .success(success)
                .build();

        return response;
    }

    @GetMapping("/self")
    public User selfUser(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email);
    }


}
