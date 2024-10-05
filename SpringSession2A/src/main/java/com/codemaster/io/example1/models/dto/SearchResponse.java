package com.codemaster.io.example1.models.dto;

import com.codemaster.io.example1.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private List<Product> products;
}
