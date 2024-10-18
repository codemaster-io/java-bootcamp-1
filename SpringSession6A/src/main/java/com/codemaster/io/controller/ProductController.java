package com.codemaster.io.controller;

import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.*;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private ProductService productService;
    private SearchService searchService;

    public ProductController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    @GetMapping( "/")
    public AllProductsResponse allProducts() {
        List<Product> products = productService.getAllProducts();
        AllProductsResponse response = AllProductsResponse.builder()
                .products(products)
                .build();
        return response;
    }

    @PostMapping( "/")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    @PutMapping( "/")
    public Product updateProduct(@RequestBody Product product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping( "/")
    public DeleteResponse deleteProduct(@PathVariable int productId) {
        boolean success = productService.deleteProduct(productId);
        DeleteResponse response = DeleteResponse.builder()
                .success(success)
                .build();
        return response;
    }

    @GetMapping("/{productId}")
    public Product getProduct(@PathVariable int productId) {
        System.out.println("productId = " + productId);
        return productService.getProduct(productId);
    }

    @GetMapping("/search")
    public SearchResponse searchProducts(@RequestParam(required = true) String query) {
        System.out.println("query = " + query);
        List<Product> products = searchService.search(query);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(products);
        return searchResponse;
    }

}
