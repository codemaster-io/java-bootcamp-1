package com.codemaster.io.repository;

import com.codemaster.io.models.Product;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
public class ProductRepository {
    private Map<Integer, Product> productMap;

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

    public boolean deleteProduct(int productId) {
        if(productMap.containsKey(productId)) {
            productMap.remove(productId);
            return true;
        }
        return false;
    }

    public Product getProduct(int productId) {
        return productMap.get(productId);
    }

    public List<Product> getProducts() {
        return (new ArrayList<>(productMap.values()));
    }
}
