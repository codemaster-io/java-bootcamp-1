package com.codemaster.io.service;

import com.codemaster.io.models.Product;
import com.codemaster.io.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product addProduct(Product product) {
        int id = (int) (System.currentTimeMillis()/1000);
        product = product.toBuilder()
                .id(id)
                .build();
        boolean success = productRepository.addProduct(product);
        System.out.println("success = " + success);
        if(success) return product;
        return null;
    }

    public Product updateProduct(Product product) {
        boolean success = productRepository.deleteProduct(product.getId());
        if(success) {
            productRepository.addProduct(product);
            return product;
        }
        return null;
    }

    public boolean deleteProduct(int productId) {
        return productRepository.deleteProduct(productId);
    }

    public Product getProduct(int id) {
        return productRepository.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }
}
