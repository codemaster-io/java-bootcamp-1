package com.codemaster.io.controller;

import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.SignInRequest;
import com.codemaster.io.models.dto.SignUpRequest;
import com.codemaster.io.models.dto.SignUpResponse;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthService authService;
    private UserService userService;

    public AuthController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }


    @PostMapping( "/register")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        int id = authService.signup(user);
        String token = "";
        if(id != -1) {
            user = userService.getUserById(id);
            token = "Generated token";
        }
        SignUpResponse response = SignUpResponse.builder()
                .user(user)
                .token(token)
                .build();
        return response;
    }

    @PostMapping( "/login")
    public SignUpResponse signIn(@RequestBody SignInRequest request) {
        User user = authService.signIn(request.getEmail(), request.getPassword());
        String token = "";
        if(user != null) {
            token = "Generated token";
        }

        SignUpResponse response = SignUpResponse.builder()
                .user(user)
                .token(token)
                .build();
        return response;
    }
}
