package com.codemaster.io.models.dto;

import com.codemaster.io.models.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class AllProductsResponse {
    private List<Product> products;
}
