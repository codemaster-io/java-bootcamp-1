package com.codemaster.io.example1.controller;

import com.codemaster.io.example1.models.Product;
import com.codemaster.io.example1.models.dto.AddProductRequest;
import com.codemaster.io.example1.models.dto.AddProductResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ProductServlet extends HttpServlet {
    private ProductController productController;

    public ProductServlet(ProductController productController) {
        this.productController = productController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uri = req.getRequestURI(); // e.g., /products/123
        System.out.println("uri = " + uri);
        String id = extractIdFromUri(uri); // Extract the id from the URI
        System.out.println("id = " + id);

        ObjectMapper objectMapper = new ObjectMapper();

        Product product = productController.getProduct(id);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(product));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = readJson(req);
        System.out.println("json = " + json);
        AddProductRequest product = objectMapper.readValue(json, AddProductRequest.class);
        System.out.println("product = " + product);

        AddProductResponse response = productController.addProduct(product);

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        resp.getWriter().write(objectMapper.writeValueAsString(response));
    }

    private String extractIdFromUri(String uri) {
        // Split the URI to extract the id
        String[] segments = uri.split("/");
        return segments[segments.length - 1]; // The last segment is the ID
    }

    public String readJson(HttpServletRequest req) {
        StringBuilder jsonBuilder = new StringBuilder();
        String line;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jsonBuilder.append(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return jsonBuilder.toString();
    }

}
