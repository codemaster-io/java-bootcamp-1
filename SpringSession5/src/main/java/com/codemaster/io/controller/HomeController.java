package com.codemaster.io.controller;

import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private SearchService searchService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("products", productService.getAllProducts());
        return "index"; // This will point to index.html
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "query", required = false) String query, Model model) {
        model.addAttribute("products", searchService.search(query));
        model.addAttribute("query", query);
        return "search"; // This will point to search.html
    }
}
