package com.codemaster.io.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@RequiredArgsConstructor
public enum Permission implements Serializable {

    ADMIN_READ_PERMISSION("ADMIN:READ_PERMISSION"),
    ADMIN_ALL_PERMISSION("ADMIN:ALL_PERMISSION"),
    MODERATOR_READ_PERMISSION("MODERATOR:READ_PERMISSION"),
    MODERATOR_ALL_PERMISSION("MODERATOR:ALL_PERMISSION");

    @JsonValue  // Serialize as JSON
    public String getName() {
        return this.getDescription();
    }

    @JsonCreator  // Deserialize from JSON
    public static Permission fromJson(String name) {
        for (Permission permission : Permission.values()) {
            if (permission.getDescription().equalsIgnoreCase(name)) {
                return permission;  // Return the enum if description matches
            }
        }
        return null;
    }

    @Getter
    private final String description;
}
