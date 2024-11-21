package com.codemaster.io.repositories;

import com.codemaster.io.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {

    // Find all tags for a specific product
//    @Query("SELECT t FROM Tag t JOIN t.products p WHERE p.id = :productId")
//    List<Tag> findTagsByProductId(int productId);
}
