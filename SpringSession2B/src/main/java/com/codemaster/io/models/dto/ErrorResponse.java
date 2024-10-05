package com.codemaster.io.models.dto;

import lombok.Data;

@Data
public class ErrorResponse {
    private int status;
    private String message;
    private String path;
}
