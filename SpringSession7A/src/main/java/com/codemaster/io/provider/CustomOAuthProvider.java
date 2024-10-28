package com.codemaster.io.provider;

import com.codemaster.io.models.Role;
import com.codemaster.io.models.User;
import com.codemaster.io.service.GithubAPIService;
import com.codemaster.io.service.GoogleAPIService;
import com.codemaster.io.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
@Component
public class CustomOAuthProvider implements AuthenticationProvider {

    @Autowired
    private UserService userService;

    @Autowired
    private GoogleAPIService googleAPIService;

    @Autowired
    private GithubAPIService githubAPIService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("CustomOAuth Authentication Provider");

        if (authentication instanceof OAuthToken) {
            OAuthToken oAuthToken = (OAuthToken) authentication;

            oAuthToken.getAuthToken();

            User user = null;
            if(oAuthToken.getTokenProvider().equals("Google")) {
                user = googleAPIService.getUserFrom(oAuthToken.getAuthToken());
            } else if(oAuthToken.getTokenProvider().equals("Github")) {
                user = githubAPIService.getUserFrom(oAuthToken.getAuthToken());
            }

            if(user == null) return null;

            // Add user if not exist in the repository
            if(userService.getUserByEmail(user.getEmail()) == null) {
                user = user.toBuilder()
                        .password("")
                        .role(Role.USER)
                        .permissions(new ArrayList<>())
                        .build();
                userService.addUser(user);
            }

            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());

            // Create an authenticated token
            return new OAuthToken(userDetails.getUsername(), "",
                    oAuthToken.getTokenProvider(),
                    oAuthToken.getTokenType(),
                    userDetails.getAuthorities());
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuthToken.class.isAssignableFrom(authentication);
    }

}
