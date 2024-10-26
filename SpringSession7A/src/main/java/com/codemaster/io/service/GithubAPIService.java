package com.codemaster.io.service;

import com.codemaster.io.models.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GithubAPIService {

    @Value("${github.client.id}")
    private String CLIENT_ID;

    @Value("${github.client.secret}")
    private String CLIENT_SECRET;

    @Value("${github.client.redirect.uri}")
    private String REDIRECT_URI;

    private static final String TOKEN_URL = "https://github.com/login/oauth/access_token";
    private static final String USER_INFO_URL = "https://api.github.com/user";
    private static final String USER_EMAILS_URL = "https://api.github.com/user/emails"; // New endpoint for emails

    private static final String REPOS_URL = "https://api.github.com/user/repos";


    private final RestTemplate restTemplate = new RestTemplate();

    public User getUserFrom(String code) {
        String accessToken = exchangeCodeForAccessToken(code);
        if (accessToken != null) {
//            fetchUserRepositories(accessToken);
            return fetchUserInfo(accessToken);
        }
        return null;
    }

    private String exchangeCodeForAccessToken(String code) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", CLIENT_ID);
        params.add("client_secret", CLIENT_SECRET);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.exchange(TOKEN_URL, HttpMethod.POST, request, Map.class);

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            // GitHub returns the access token in the response body
            return (String) response.getBody().get("access_token");
        }
        return null;
    }

    private User fetchUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken); // Set the access token in the Authorization header

        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<Map> userInfoResponse = restTemplate.exchange(USER_INFO_URL, HttpMethod.GET, entity, Map.class);

        if (userInfoResponse.getStatusCode() == HttpStatus.OK && userInfoResponse.getBody() != null) {
            Map<String, Object> userInfo = userInfoResponse.getBody();
            String name = (String) userInfo.get("name");

            ResponseEntity<List> emailsResponse = restTemplate.exchange(USER_EMAILS_URL, HttpMethod.GET, entity, List.class);
            String email = null;

            if (emailsResponse.getStatusCode() == HttpStatus.OK && emailsResponse.getBody() != null) {
                List<Map<String, Object>> emailsList = emailsResponse.getBody();
                // Use the primary email, if it exists
                for (Map<String, Object> emailEntry : emailsList) {
                    if ((Boolean) emailEntry.get("primary")) {
                        email = (String) emailEntry.get("email");
                        break;
                    }
                }
            }
            System.out.println("email = " + email);
            System.out.println("userInfo = " + userInfo);

            return User.builder()
                    .email(email)
                    .name(name)
                    .build();
        }
        return null;
    }

    private List<String> fetchUserRepositories(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            ResponseEntity<List<Map<String, Object>>> response = restTemplate.exchange(
                    REPOS_URL,
                    HttpMethod.GET,
                    entity,
                    new ParameterizedTypeReference<List<Map<String, Object>>>() {}
            );
//            System.out.println("response = " + response);

            if (response.getStatusCode() == HttpStatus.OK) {
                List<String> repos = response.getBody().stream()
                        .map(repo -> (String) repo.get("html_url"))
                        .collect(Collectors.toList());
                System.out.println("repos = " + repos);
                return repos;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
