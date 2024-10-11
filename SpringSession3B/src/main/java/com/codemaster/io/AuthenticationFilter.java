package com.codemaster.io;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class AuthenticationFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init AuthenticationFilter");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {

        System.out.println("DoFilter AuthenticationFilter");

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String token = httpRequest.getHeader("Authorization");

        if (token != null && token.equals("valid-token")) {
            chain.doFilter(request, response);  // Pass the request along the chain
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Unauthorized");
        }
    }

    public void destroy() {
        System.out.println("Cleanup AuthenticationFilter");
    }
}