package com.codemaster.io.service;


import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.models.SessionData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomHttpSession {
    private Map<String, SessionData> sessionMap = new HashMap<>();
    private final static String CUSTOM_SESSION_NAME = "CUSTOM_SESSION";

    public SessionData getSession(String sessionId) {
        return sessionMap.get(sessionId);
    }

    public SessionData createSession(String sessionId, String username, HttpServletResponse response) {
        SessionData sessionData = SessionData.builder()
                .createdTime(System.currentTimeMillis())
                .lastLoginTime(System.currentTimeMillis())
                .id(sessionId)
                .name(username)
                .build();
        sessionMap.put(sessionId, sessionData);

        Cookie cookie = new Cookie(CUSTOM_SESSION_NAME, sessionId);
        cookie.setMaxAge(86400);  // 1 day in seconds
        cookie.setPath("/"); // Make it accessible site-wide

        response.addCookie(cookie);

        return sessionData;
    }

    public SessionData findSession(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for (Cookie cookie : cookies) {
                if(cookie.getName().equals(CUSTOM_SESSION_NAME)) {
                    if(sessionMap.containsKey(cookie.getValue())) {
                        return sessionMap.get(cookie.getValue());
                    }
                }
            }
        }
        return null;
    }

    public void deleteSession(String sessionId) {
        sessionMap.remove(sessionId);
    }
}
