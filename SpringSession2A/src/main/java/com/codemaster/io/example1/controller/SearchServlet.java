package com.codemaster.io.example1.controller;

import com.codemaster.io.example1.models.Product;
import com.codemaster.io.example1.models.dto.SearchResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private ProductController productController;

    public SearchServlet(ProductController productController) {
        this.productController = productController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String searchQuery = req.getParameter("query");

        ObjectMapper objectMapper = new ObjectMapper();

        SearchResponse searchResponse = productController.search(searchQuery);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(searchResponse));
    }
}
