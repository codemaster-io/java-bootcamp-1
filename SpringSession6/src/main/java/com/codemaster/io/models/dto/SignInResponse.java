package com.codemaster.io.models.dto;

import com.codemaster.io.models.User;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder(toBuilder = true)
public class SignInResponse {
    private long id;
    private String name;
    private String email;
    private String role;
    private List<String> permissions;
    private String token;
}
