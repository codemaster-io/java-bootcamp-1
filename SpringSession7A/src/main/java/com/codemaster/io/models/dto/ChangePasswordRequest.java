package com.codemaster.io.models.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class ChangePasswordRequest {
    private String currentPassword;
    private String newPassword;
}
