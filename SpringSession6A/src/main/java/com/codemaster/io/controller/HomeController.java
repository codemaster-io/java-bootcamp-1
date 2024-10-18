package com.codemaster.io.controller;

import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    private ProductService productService;

    private SearchService searchService;

    public HomeController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    @GetMapping("/")
    public String home() {
        return "index"; // This will point to index.html
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard"; // This will point to index.html
    }

    @GetMapping("/admin")
    public String search() {
        return "admin";
    }
}
