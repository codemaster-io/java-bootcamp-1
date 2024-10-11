package com.codemaster.io.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class User {
    private String name;
    private String username;
    private String password;
}
