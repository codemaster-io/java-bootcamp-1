package com.codemaster.io.service;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.litespring.context.UserContext;
import com.codemaster.io.models.SessionData;
import com.codemaster.io.repository.UserRepository;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.HashMap;
import java.util.Map;

@Component
public class CustomSessionService {
    private Map<String, SessionData> sessionDataMap = new HashMap<>();
    private static final String CUSTOM_SESSION_ID = "CUSTOM_SESSION_ID";

    @Autowired
    private UserRepository userRepository;

    public SessionData createSession(String sessionId, String username) {
        if(sessionDataMap.containsKey(sessionId)) return null;
        SessionData sessionData = SessionData.builder()
                .id(sessionId)
                .username(username)
                .createdTime(System.currentTimeMillis())
                .lastLoginTime(System.currentTimeMillis())
                .build();
        sessionDataMap.put(sessionId, sessionData);

        return sessionData;
    }

    public SessionData findSessionAndSetContext(HttpServletRequest request) {
        String token = request.getParameter("token");
        if(sessionDataMap.containsKey(token)) {
            SessionData sessionData = sessionDataMap.get(token);
            System.out.println("sessionData.getUsername() = " + sessionData.getUsername());
            UserContext.setUserContext(userRepository.getUser(sessionData.getUsername()));
            return sessionData;
        }
        return null;
    }

    public void deleteSession(String sessionId) {
        sessionDataMap.remove(sessionId);
    }
}
