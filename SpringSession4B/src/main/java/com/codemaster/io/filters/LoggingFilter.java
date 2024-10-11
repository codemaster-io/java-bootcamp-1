package com.codemaster.io.filters;

import jakarta.servlet.*;

import java.io.IOException;

public class LoggingFilter implements Filter {
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init LoggingFilter");
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        chain.doFilter(request, response);
//        System.out.println("DoFilter LoggingFilter");
//        System.out.println("Request received from: " + request.getRemoteAddr());
//        chain.doFilter(request, response);  // Continue to next filter or servlet
//        System.out.println("Response sent.");
    }
}