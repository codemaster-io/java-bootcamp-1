package com.codemaster.io.controller;

import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public UsersListResponse getAllUsers() {
        List<User> users = userService.getAllUsers();
        UsersListResponse response = UsersListResponse.builder()
                .users(users)
                .build();
        return response;
    }

    @GetMapping("/{userId}")
    public User getUser(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return user;
    }

    @PostMapping
    public AddUserResponse addUser(@RequestBody AddUserRequest req) {
        System.out.println("Add user");
        User user = User.builder()
                .name(req.getName())
                .email(req.getEmail())
                .password(req.getPassword())
                .role(req.getRole())
                .permissions(req.getPermissions())
                .build();

        long id = userService.addUser(user);
        AddUserResponse response = AddUserResponse.builder().build();

        if (id != -1) {
            response = response.toBuilder()
                    .success(true)
                    .user(userService.getUserById(id))
                    .build();
        }
        return response;
    }

    @PutMapping
    public UpdateUserResponse updateUser(@RequestBody UpdateUserRequest req) {
        User user = userService.getUserById(req.getId());
        UpdateUserResponse response = UpdateUserResponse.builder().build();

        if(user == null) return response;
        user = user.toBuilder()
                .name(req.getName())
                .email(req.getEmail())
                .role(req.getRole())
                .permissions(req.getPermissions())
                .build();
        boolean success = userService.updateUser(user);
        if(success) {
            response = response.toBuilder()
                    .success(success)
                    .user(userService.getUserById(req.getId()))
                    .build();
        }
        return response;
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public DeleteResponse deleteUser(@PathVariable long userId) {
        boolean success = userService.deleteUserById(userId);
        DeleteResponse response = DeleteResponse.builder()
                .success(success)
                .build();

        return response;
    }
}
