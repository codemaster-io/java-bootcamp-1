package com.codemaster.io.repository;

import com.codemaster.io.models.Product;
import com.codemaster.io.models.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, String> {


}
