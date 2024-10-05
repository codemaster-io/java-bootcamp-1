package io.codemaster.demo.custom_annotation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    private String name;

    @CustomValue(minValue = 10)
    private int price;
}
