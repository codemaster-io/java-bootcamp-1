package com.codemaster.io.controller;

import com.codemaster.io.litespring.context.UserContext;
import com.codemaster.io.litespring.enums.MethodType;
import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.LoginRequest;
import com.codemaster.io.models.dto.RegisterRequest;
import com.codemaster.io.models.dto.RegisterResponse;
import com.codemaster.io.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;


@RestController(url = "/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @RequestMapping(url = "/auth/register", type = MethodType.POST)
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

    @RequestMapping(url = "/auth/login", type = MethodType.POST)
    public User login(@RequestBody LoginRequest loginRequest, HttpServletRequest request) {
        User user = authService.signIn(loginRequest.getUsername(), loginRequest.getPassword());
        if(user.getUsername().equals(loginRequest.getUsername())) {
            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("username", user.getUsername());
        }
        return user;
    }

    @RequestMapping(url = "/users/{id}", type = MethodType.GET)
    public User user(@PathVariable(value = "id")  String id) {
        if(UserContext.getUserContext() != null) {
            System.out.println("UserContext.getUserContext() = " + UserContext.getUserContext());
            User user = authService.getUser(id);
            return user;
        }
        return User.builder().build();
    }

    @RequestMapping(url = "/self", type = MethodType.GET)
    public User selfUser() {
        System.out.println("Self User");
        if(UserContext.getUserContext() != null) {
            System.out.println("UserContext.getUserContext() = " + UserContext.getUserContext());
            User user = authService.getUser(UserContext.getUserContext());
            System.out.println("user = " + user);
            return user;
        }
        return User.builder().build();
    }

}
