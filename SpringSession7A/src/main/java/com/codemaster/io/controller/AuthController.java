package com.codemaster.io.controller;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.User;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.UserService;
import com.codemaster.io.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

        System.out.println("request.getEmail() = " + request.getEmail());
        Authentication authentication = authService.authenticate(
                request.getEmail(), request.getPassword(), request.getAuthToken(),
                request.getProvider(), request.getTokenType());

        User user = User.builder().build();
        String token = "";

        System.out.println("isAuthenticated = " + authentication.isAuthenticated());

        if(authentication.isAuthenticated()) {
            user = userService.getUserByEmail(authentication.getName());
            System.out.println("user = " + user);

            token = jwtUtils.generateTokenFromUser(user);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            System.out.println("token = " + token);
        }

        return SignInResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .imageUrl(user.getImageUrl())
                .role(user.getRole().toString())
                .permissions(user.getPermissions().stream()
                        .map(Permission::toString).collect(Collectors.toList()))
                .token(token)
                .build();
    }

    @PostMapping( "/signup")
    public SignUpResponse signUp(@RequestBody SignUpRequest request) {
        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .build();
        long id = authService.signup(user);
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

    @PostMapping("/change-password")
    public ChangePasswordResponse changePassword(@RequestBody ChangePasswordRequest request,
            Principal requestedUser) {
        return null;
    }

    @GetMapping("/user")
    public User selfUser(Principal principal) {
        String email = principal.getName();
        return userService.getUserByEmail(email);
    }
}
