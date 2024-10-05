package io.codemaster.demo.example3.service;

import io.codemaster.demo.example3.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SearchService {

    private ProductService productService;

    public SearchService(@Autowired ProductService productService) {
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
