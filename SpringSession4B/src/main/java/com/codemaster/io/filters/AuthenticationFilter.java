package com.codemaster.io.filters;

import com.codemaster.io.litespring.context.UserContext;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class AuthenticationFilter implements Filter {

    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("Init AuthenticationFilter");
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain)
            throws IOException, ServletException {

        System.out.println("Do AuthenticationFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpSession httpSession = request.getSession(false);

        if(httpSession != null) {
            UserContext.setUserContext((String)httpSession.getAttribute("username"));
        }

        chain.doFilter(servletRequest, servletResponse);

        UserContext.clear();
    }

    public void destroy() {
        System.out.println("Cleanup AuthenticationFilter");
    }
}