package com.codemaster.io.models.dto;

import com.codemaster.io.models.Product;
import lombok.Data;

import java.util.List;

@Data
public class SearchResponse {
    private List<Product> products;
}
