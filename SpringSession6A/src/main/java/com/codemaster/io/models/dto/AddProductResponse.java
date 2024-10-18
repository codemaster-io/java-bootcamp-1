package com.codemaster.io.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class AddProductResponse {
    private int id;
}
