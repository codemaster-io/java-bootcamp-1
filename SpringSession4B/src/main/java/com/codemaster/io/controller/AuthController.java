package com.codemaster.io.controller;

import com.codemaster.io.litespring.MethodType;
import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.LoginRequest;
import com.codemaster.io.models.dto.RegisterRequest;
import com.codemaster.io.models.dto.RegisterResponse;
import com.codemaster.io.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;


@RestController(url = "/api/users")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(url = "/register", type = MethodType.POST)
    public RegisterResponse register(@RequestBody RegisterRequest request) {
        System.out.println("request = " + request);
        User user = User.builder()
                .name(request.getName())
                .password(request.getPassword())
                .build();
        System.out.println("user = " + user);

        user = authService.register(user);
        RegisterResponse response = RegisterResponse.builder()
                .user(user)
                .build();
        return response;
    }

    @RequestMapping(url = "/login", type = MethodType.POST)
    public User login(@RequestBody LoginRequest loginRequest, HttpServletRequest servletRequest) {
        System.out.println("servletRequest.getRequestURI() = " + servletRequest.getRequestURI());
        return authService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @RequestMapping(url = "/{id}", type = MethodType.GET)
    public User user(@PathVariable(value = "id")  String id) {
        User user = authService.getUser(id);
        return user;
    }

}
