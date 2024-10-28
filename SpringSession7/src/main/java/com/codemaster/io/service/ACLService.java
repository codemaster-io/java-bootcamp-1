package com.codemaster.io.service;

import com.codemaster.io.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service("ACLService")
public class ACLService {
    @Autowired
    private ProductService productService;
    public boolean hasPermitToDelete(long productId) {
        try {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            Product product = productService.getProduct(productId);
            if (product == null) return false;
            if (product.getAddedByUserEmail().equals(userEmail)) return true;
            return false;
        } catch (Exception exception) {
            exception.printStackTrace();
            return false;
        }
    }
}
