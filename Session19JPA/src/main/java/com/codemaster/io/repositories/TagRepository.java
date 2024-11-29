package com.codemaster.io.repositories;

import com.codemaster.io.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
    @Query("SELECT t FROM Tag t WHERE t.id IN :tagIds")
    List<Tag> findTagsByTagIds(List<String> tagIds);
}
