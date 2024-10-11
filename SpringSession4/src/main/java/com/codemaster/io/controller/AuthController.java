package com.codemaster.io.controller;

import com.codemaster.io.litespring.MethodType;
import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.LoginRequest;
import com.codemaster.io.models.dto.RegisterRequest;
import com.codemaster.io.models.dto.RegisterResponse;
import com.codemaster.io.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(url = "/api/register", type = MethodType.POST)
    RegisterResponse register(@RequestBody RegisterRequest request) {
        User user = User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .build();
        user = authService.register(user);
        RegisterResponse response = RegisterResponse.builder()
                .user(user)
                .build();
        return response;
    }

    @RequestMapping(url = "/api/user/login", type = MethodType.POST)
    User login(@RequestBody LoginRequest request, HttpServletRequest servletRequest) {
        return authService.signIn(request.getUsername(), request.getPassword());
    }

    @Authenticated
    @RequestMapping(url = "/api/user/{id}", type = MethodType.GET)
    User user(@PathVariable(value = "id")  String id) {
        User user = authService.getUser(id);
        return user;
    }

}
