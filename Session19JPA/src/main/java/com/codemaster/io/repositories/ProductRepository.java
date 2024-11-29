package com.codemaster.io.repositories;

import com.codemaster.io.models.Product;
import com.codemaster.io.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find products by a specific tag name
    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.id = :tagId")
    List<Product> findProductsByTagId(String tagId);

    @Query("SELECT p FROM Product p JOIN p.tags t WHERE t.id IN :tagIds")
    List<Product> findProductsByTagIds(List<String> tagIds);

    @Query("SELECT t FROM Product p JOIN p.tags t WHERE p.id = :productId")
    List<Tag> findTagsByProductId(String productId);
}
