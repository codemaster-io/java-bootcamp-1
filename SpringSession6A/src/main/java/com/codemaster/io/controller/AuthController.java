package com.codemaster.io.controller;

import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.SignInRequest;
import com.codemaster.io.models.dto.SignUpRequest;
import com.codemaster.io.models.dto.SignUpResponse;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

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
        Authentication authentication;
        try {
            authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        } catch (AuthenticationException exception) {
            throw exception;
        }

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

    @GetMapping("/user")
    public User selfUser(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email);
    }
}
