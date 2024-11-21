package com.codemaster.io.repositories;

import com.codemaster.io.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find products by a specific tag name
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.name = :tagName")
    List<Product> findProductsByTagName(String tagName);
}
