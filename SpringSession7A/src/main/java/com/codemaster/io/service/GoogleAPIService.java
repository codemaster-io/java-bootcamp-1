package com.codemaster.io.service;

import com.codemaster.io.models.User;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.Date;
import java.util.Map;

@Service
public class GoogleAPIService {

    private static final String GOOGLE_JWKS_URL = "https://www.googleapis.com/oauth2/v3/certs";
    private static final String GOOGLE_ISSUER = "https://accounts.google.com";

    @Value("${google.client.id}")
    private String CLIENT_ID;

    @Value("${google.client.secret}")
    private String CLIENT_SECRET;

    @Value("${google.client.redirect.uri}")
    private String REDIRECT_URL;

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    private final RestTemplate restTemplate = new RestTemplate();

    public User getUserFrom(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);
        params.add("redirect_uri", REDIRECT_URL);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(TOKEN_URL, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> tokens = response.getBody();
            System.out.println("tokens = " + tokens);
            String accessToken = (String) tokens.get("access_token");
            String refreshToken = (String) tokens.get("refresh_token");
            String idToken = (String) tokens.get("id_token");

            System.out.println("refreshToken = " + refreshToken);
            System.out.println("accessToken = " + accessToken);
            System.out.println("idToken = " + idToken);

            return verify(idToken);
        }

        return null;
    }

    public User verify(String idToken) {
        try {
            System.out.println("idToken google service = " + idToken);
            SignedJWT signedJWT = SignedJWT.parse(idToken);

            JWKSet jwkSet = JWKSet.load(new URL(GOOGLE_JWKS_URL));

            RSAKey rsaKey = (RSAKey) jwkSet.getKeyByKeyId(signedJWT.getHeader().getKeyID());
            if (rsaKey == null || !rsaKey.getAlgorithm().equals(JWSAlgorithm.RS256)) {
                throw new IllegalArgumentException("Invalid or unsupported key.");
            }

            if (!signedJWT.verify(new RSASSAVerifier(rsaKey))) {
                throw new IllegalArgumentException("Invalid token signature.");
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if (!claims.getIssuer().equals(GOOGLE_ISSUER)) {
                throw new IllegalArgumentException("Invalid token issuer.");
            }
            if (!claims.getAudience().contains(CLIENT_ID)) {
                throw new IllegalArgumentException("Invalid token audience.");
            }
            if (claims.getExpirationTime().before(new Date())) {
                throw new IllegalArgumentException("Token has expired.");
            }

            // Return the claims if verification succeeds
            // Extract the user info from claims
            String email = claims.getStringClaim("email");
            String name = claims.getStringClaim("name");
            String image = claims.getStringClaim("picture");

            return User.builder()
                    .email(email)
                    .name(name)
                    .imageUrl(image)
                    .build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }


}
