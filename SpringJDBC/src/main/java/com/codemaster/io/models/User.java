package com.codemaster.io.models;

import lombok.Builder;
import lombok.Data;

@Builder(toBuilder = true)
@Data
public class User {
    private Integer id;
    private String name;

    private String email;

    private Integer age;

    private String country;
}
