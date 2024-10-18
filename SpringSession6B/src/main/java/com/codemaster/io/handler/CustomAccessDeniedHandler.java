package com.codemaster.io.handler;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.out.println("AccessDeniedHandler Exception");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // Set status code to 403
        response.sendRedirect(request.getContextPath() + "/error_403"); // Change this to your error page URL
    }
}
