package com.codemaster.io.utils;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.Role;
import com.codemaster.io.models.User;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${jwtSecretKey}")
    private String jwtSecret;

    @Value("${jwtExpirationMs}")
    private int jwtExpirationMs;

    public String getJwtFromHeader(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");  // Get the Authorization header

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // Remove the "Bearer " prefix to get the token
        }

        return null; // Return null if no JWT token is found
    }

    public String generateTokenFromUser(User user) {
        try {
            return Jwts.builder()
                    .subject(user.getEmail())
                    .claim("id", user.getId())
                    .claim("name", user.getName())
                    .claim("email", user.getEmail())
                    .claim("role", user.getRole())
                    .claim("permissions", user.getPermissions().stream()
                            .map(Permission::getDescription).collect(Collectors.toList()))
                    .issuedAt(new Date())
                    .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                    .signWith(key())
                    .compact();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) key())
                .build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith((SecretKey) key()).build().parseSignedClaims(authToken);
            return true;
        } catch (Exception e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        }
        return false;
    }
}