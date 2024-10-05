package io.codemaster.demo.example3.repository;

import io.codemaster.demo.example3.models.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductRepository {
    private Map<String, Product> productMap;

    public ProductRepository() {
        productMap = new HashMap<>();
    }

    public boolean addProduct(Product product) {
        if(productMap.containsKey(product.getId())) return false;
        productMap.put(product.getId(), product);
        return true;
    }

    public Product getProduct(String productId) {
        return productMap.get(productId);
    }

    public List<Product> getProducts() {
        return (new ArrayList<>(productMap.values()));
    }
}
