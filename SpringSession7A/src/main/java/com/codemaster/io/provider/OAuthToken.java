package com.codemaster.io.provider;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OAuthToken extends AbstractAuthenticationToken {

    private final String authToken;
    private final String tokenProvider;
    private final String tokenType;
    private String userEmail;

    // Constructor that receives the authentication token, provider, and type
    public OAuthToken(String authToken, String tokenProvider, String tokenType) {
        super(null); // Pass null as authorities initially
        this.authToken = authToken;
        this.tokenProvider = tokenProvider;
        this.tokenType = tokenType;
        super.setAuthenticated(false); // This token is not yet authenticated
    }

    public OAuthToken(String userEmail, String authToken, String tokenProvider, String tokenType,
                      Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.userEmail = userEmail;
        this.authToken = authToken;
        this.tokenProvider = tokenProvider;
        this.tokenType = tokenType;
        super.setAuthenticated(true); // This token is now authenticated
    }


    @Override
    public Object getCredentials() {
        return authToken;
    }

    @Override
    public Object getPrincipal() {
        return userEmail;
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getTokenProvider() {
        return tokenProvider;
    }

    public String getTokenType() {
        return tokenType;
    }
}
