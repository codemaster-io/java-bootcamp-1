package com.codemaster.io.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Permission {

    ADMIN_READ_PERMISSION("ADMIN:READ_PERMISSION"),
    ADMIN_ALL_PERMISSION("ADMIN:ALL_PERMISSION"),
    MODERATOR_READ_PERMISSION("MODERATOR:READ_PERMISSION"),
    MODERATOR_ALL_PERMISSION("MODERATOR:ALL_PERMISSION");

    @Getter
    private final String permission;
}
