package com.codemaster.io.models.dto;

import com.codemaster.io.models.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterResponse {
    private User user;
}
