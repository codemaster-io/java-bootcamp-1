package com.codemaster.io.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SignUpRequest {
    private String email;
    private String name;
    private String password;
}
