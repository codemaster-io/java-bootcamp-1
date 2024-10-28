package com.codemaster.io.models.dto;

import com.codemaster.io.models.Product;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AddProductResponse {
    private Product product;
    private boolean success;
}
