package io.codemaster.demo.example4.controller;

import io.codemaster.demo.example4.models.Product;
import io.codemaster.demo.example4.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class RestAPIController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/hello", produces = "application/json")
    public String hello() {
        return "Hello World";
    }

    @PostMapping(value = "/product", produces = "application/json")
    public String addProduct(@RequestBody Product product) {
        System.out.println("product.getName() = " + product.getName());
        String productId = productService.addProduct(product);
        System.out.println("productId = " + productId);
        return productId;
    }
}


//curl --location 'http://localhost:8080/api/product' \
//        --header 'Content-Type: application/json' \
//        --data '{
//        "name": "iphone 16"
//        }'
