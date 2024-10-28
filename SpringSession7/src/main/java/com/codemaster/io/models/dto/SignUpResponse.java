package com.codemaster.io.models.dto;

import com.codemaster.io.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SignUpResponse {
    private User user;
    private String token;
}
