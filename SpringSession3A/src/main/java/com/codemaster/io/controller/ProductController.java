package com.codemaster.io.controller;


import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.dto.AddProductRequest;
import com.codemaster.io.models.dto.AddProductResponse;
import com.codemaster.io.models.dto.SearchResponse;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.SearchService;

import java.util.List;

@Component
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private SearchService searchService;

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
