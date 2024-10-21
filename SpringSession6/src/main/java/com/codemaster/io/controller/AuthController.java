package com.codemaster.io.controller;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.UserService;
import com.codemaster.io.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping( "/signin")
    public SignInResponse signIn(@RequestBody SignInRequest request) {
        try {
            Authentication authentication = authService.passwordMatch(request.getEmail(), request.getPassword());
            String token = "";
            SignInResponse response = SignInResponse.builder().build();

            if (authentication.isAuthenticated()) {
                User user = userService.getUserByEmail(request.getEmail());
                token = jwtUtils.generateTokenFromUser(user);
                System.out.println("token = " + token);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                response = response.toBuilder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .role(user.getRole().toString())
                        .token(token)
                        .build();
            }
            return response;
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @GetMapping("/user")
    public User selfUser() {
        return null;
    }
}
