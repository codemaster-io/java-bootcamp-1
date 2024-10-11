package com.codemaster.io.threadlocal;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class UserContextFilter implements Filter {
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        // Assuming user is authenticated and we have the user ID
        String username = ((HttpServletRequest) request).getHeader("X-User");

        // Store user information in ThreadLocal for this thread/request
        UserContext.setUserContext(username);

        // Continue with the request processing
        chain.doFilter(request, response);

        // Clear ThreadLocal data after request processing
        UserContext.clear();
    }
}
