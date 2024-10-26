package com.codemaster.io.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class SignInRequest {
    private String email;
    private String password;

    private String provider; //Google/Github/Facebook
    private String authToken;
    private String tokenType; // code/jwt_id_token
}
