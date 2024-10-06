package com.codemaster.io.controller;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Servlet;
import com.codemaster.io.models.dto.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Servlet(urlMapping = "/api/products/search")
public class SearchServlet extends HttpServlet {
    @Autowired
    private ProductController productController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String searchQuery = req.getParameter("query");
        System.out.println("searchQuery = " + searchQuery);

        ObjectMapper objectMapper = new ObjectMapper();

        SearchResponse searchResponse = productController.search(searchQuery);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(searchResponse));
    }
}
