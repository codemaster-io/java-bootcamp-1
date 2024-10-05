package io.codemaster.demo.example4.repository;

import io.codemaster.demo.example4.models.Product;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductRepository {
    private Map<String, Product> productMap;

    public ProductRepository() {
        productMap = new HashMap<>();
    }

    @Value("${max.size}")
    private int MAX_SIZE;

    @PostConstruct
    private void postConstruct() {
        System.out.println("Post Construct call.");
    }

    public boolean addProduct(Product product) {
        if(productMap.size() >= MAX_SIZE) return false;

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
