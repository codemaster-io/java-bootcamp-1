package com.codemaster.io.services;

import com.codemaster.io.models.Product;
import com.codemaster.io.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Find products by tag name
    public List<Product> findProductsByTag(String tagName) {
        return productRepository.findProductsByTagName(tagName);
    }

    // Save a product
    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }
}