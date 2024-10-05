package com.codemaster.io.example1.service;


import com.codemaster.io.example1.models.Product;

import java.util.ArrayList;
import java.util.List;

public class SearchService {

    private ProductService productService;

    public SearchService(ProductService productService) {
        this.productService = productService;
    }

    public List<Product> search(String name) {
        List<Product> filterProducts = new ArrayList<>();

        List<Product> products = productService.getAllProducts();
        for(Product product : products) {
            if(product.getName().toLowerCase().contains(name)) filterProducts.add(product);
        }
        return filterProducts;
    }
}
