package org.example.records;

public record UserRequestDTO (
        String firstName,
        String lastName,
        String email,
        int age
) {}
