package com.codemaster.io.service;

import com.codemaster.io.models.User;
import com.codemaster.io.provider.OAuthToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AuthService {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.client.redirect.uri}")
    private String redirectUri;

    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;


    private AuthService(UserService userService) {
        this.userService = userService;
    }

    public Authentication authenticate(String email, String password,
                                       String authToken, String provider, String tokenType) {
        Authentication authentication;
        try {
            if(authToken == null || authToken.equals("")) {
                authentication = authenticationManager
                        .authenticate(new UsernamePasswordAuthenticationToken(email, password));
            } else {
                authentication = authenticationManager.authenticate(
                        new OAuthToken(authToken, provider, tokenType));
            }
            return authentication;
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
        }
        return null;
    }

    public long signup(User user) {
        return userService.addUser(user);
    }

}
