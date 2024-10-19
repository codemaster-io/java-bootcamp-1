package com.codemaster.io.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class UpdateProductRequest {
    private long id;
    private String name;
    private double price;
    private String description;
}

