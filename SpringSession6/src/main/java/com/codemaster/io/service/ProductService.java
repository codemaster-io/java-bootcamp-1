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
        long id = System.currentTimeMillis();

        product = product.toBuilder()
                .id(id)
                .build();
        boolean success = productRepository.addProduct(product);
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

    public boolean deleteProduct(long productId) {
        return productRepository.deleteProduct(productId);
    }

    public Product getProduct(long id) {
        return productRepository.getProduct(id);
    }

    public List<Product> getAllProducts() {
        return productRepository.getProducts();
    }
}
