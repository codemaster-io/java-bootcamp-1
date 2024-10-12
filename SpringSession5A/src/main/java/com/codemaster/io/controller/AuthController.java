package com.codemaster.io.controller;

import com.codemaster.io.litespring.annotation.*;
import com.codemaster.io.litespring.context.UserContext;
import com.codemaster.io.litespring.enums.MethodType;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.DeleteResponse;
import com.codemaster.io.models.dto.LoginResponse;
import com.codemaster.io.models.dto.RegisterResponse;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.CustomSessionService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

import java.util.Arrays;
import java.util.UUID;

@RestController(url = "/api")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomSessionService customSessionService;

    @RequestMapping(url = "/auth/register", type = MethodType.GET)
    public RegisterResponse register(@RequestParam("username") String username, @RequestParam("password") String password) {

        User user = User.builder()
                .username(username)
                .password(password)
                .roles(Arrays.asList("ADMIN"))
                .build();

        boolean success = authService.register(user);
        if(success) return RegisterResponse.builder()
                .user(user)
                .build();

        return RegisterResponse.builder().build();
    }

    @RequestMapping(url = "/auth/login", type = MethodType.GET)
    public LoginResponse login(@RequestParam("username") String username, @RequestParam("password") String password,
                               HttpServletResponse response) {
        User user = authService.login(username, password);
        if(user != null) {
            String sessionId = UUID.randomUUID().toString();
            customSessionService.createSession(sessionId, username);

            return LoginResponse.builder()
                    .sessionId(sessionId)
                    .build();
        }
        return null;
    }

    @RequestMapping(url = "/users/{id}", type = MethodType.GET)
    public User user(@PathVariable(value = "id")  String id) {
        return User.builder().build();
    }

    @RequestMapping(url = "/user/self", type = MethodType.GET)
    public User selfUser() {
        System.out.println("UserContext.getUserContext() = " + UserContext.getUserContext());
        if(UserContext.getUserContext() != null) {
            return authService.getUser(UserContext.getUserContext().getUsername());
        }
        return null;
    }

    @Authenticated(roles = {"SUPER_ADMIN"})
    @RequestMapping(url = "/user/self/delete", type = MethodType.GET)
    public DeleteResponse selfDelete() {
        System.out.println("Self Delete");
        if(UserContext.getUserContext() != null) {
            boolean success = authService.deleteUser(UserContext.getUserContext().getUsername());
            return DeleteResponse.builder()
                    .success(success)
                    .build();
        }
        return DeleteResponse.builder().build();
    }

    @RequestMapping(url = "/user/transfer", type = MethodType.GET)
    public DeleteResponse selfDelete(@RequestParam("toUser") String toUser) {
        if(UserContext.getUserContext() != null) {
            boolean success = authService.deleteUser(UserContext.getUserContext().getUsername());
            return DeleteResponse.builder()
                    .success(success)
                    .build();
        }
        return DeleteResponse.builder().build();
    }

}
