package com.codemaster.io.controller;

import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.litespring.context.UserContext;
import com.codemaster.io.litespring.enums.MethodType;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.DeleteResponse;
import com.codemaster.io.models.dto.LoginRequest;
import com.codemaster.io.models.dto.RegisterRequest;
import com.codemaster.io.models.dto.RegisterResponse;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.CustomHttpSession;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@RestController(url = "/api")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private CustomHttpSession customHttpSession;

    @RequestMapping(url = "/auth/register", type = MethodType.GET)
    public RegisterResponse register(@RequestParam("username") String username, @RequestParam("password") String password) {

        User user = User.builder()
                .username(username)
                .password(password)
                .roles(Arrays.asList("ADMIN"))
                .build();

        user = authService.register(user);
        RegisterResponse response = RegisterResponse.builder()
                .user(user)
                .build();
        return response;
    }

    @RequestMapping(url = "/auth/login", type = MethodType.GET)
    public User login(@RequestParam("username") String username, @RequestParam("password") String password,
                      HttpServletResponse response) {
        User user = authService.signIn(username, password);
        if(user != null) {
            String sessionId = UUID.randomUUID().toString();

            customHttpSession.createSession(sessionId, username, response);
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

    @RequestMapping(url = "/user/self", type = MethodType.GET)
    public User selfUser() {
        System.out.println("UserContext.getUserContext() = " + UserContext.getUserContext());
        if(UserContext.getUserContext() != null) {
            System.out.println("UserContext.getUserContext() = " + UserContext.getUserContext());
            User user = authService.getUser(UserContext.getUserContext());
            System.out.println("user = " + user);
            return user;
        }
        return User.builder().build();
    }

    @RequestMapping(url = "/user/self", type = MethodType.POST)
    public DeleteResponse selfDelete() {
        System.out.println("Self Delete");
        if(UserContext.getUserContext() != null) {
            boolean success = authService.deleteUser(UserContext.getUserContext());
            return DeleteResponse.builder()
                    .success(success)
                    .build();
        }
        return DeleteResponse.builder().build();
    }

}
