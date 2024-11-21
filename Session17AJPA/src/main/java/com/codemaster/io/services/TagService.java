package com.codemaster.io.services;

import com.codemaster.io.models.Tag;
import com.codemaster.io.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    // Find tags by product ID
//    public List<Tag> findTagsByProduct(int productId) {
//        return tagRepository.findTagsByProductId(productId);
//    }

    // Save a tag
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }
}
