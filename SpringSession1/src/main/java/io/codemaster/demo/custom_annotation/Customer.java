package io.codemaster.demo.custom_annotation;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String name;

    @CustomValue(value = 5000)
    private int balance;
}
