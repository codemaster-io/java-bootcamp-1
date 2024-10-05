package com.codemaster.io.example1.controller;

import com.codemaster.io.example1.models.Product;
import com.codemaster.io.example1.models.dto.AddProductRequest;
import com.codemaster.io.example1.models.dto.AddProductResponse;
import com.codemaster.io.example1.models.dto.SearchResponse;
import com.codemaster.io.example1.service.ProductService;
import com.codemaster.io.example1.service.SearchService;

import java.util.List;

public class ProductController {
    private ProductService productService;
    private SearchService searchService;

    public ProductController(ProductService productService, SearchService searchService) {
        this.productService = productService;
        this.searchService = searchService;
    }

    public AddProductResponse addProduct(AddProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());

        String id = productService.addProduct(product);

        AddProductResponse addProductResponse = new AddProductResponse();
        addProductResponse.setId(id);

        return addProductResponse;
    }

    public Product getProduct(String id) {
        return productService.getProduct(id);
    }

    public SearchResponse search(String name) {
        List<Product> productList = searchService.search(name);
        SearchResponse searchResponse = new SearchResponse();
        searchResponse.setProducts(productList);
        return searchResponse;
    }
}
