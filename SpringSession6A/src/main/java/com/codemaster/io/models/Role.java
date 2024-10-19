package com.codemaster.io.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
public enum Role implements Serializable {

    USER(Collections.emptyList()),
    ADMIN(
            Arrays.asList(
                    Permission.ADMIN_READ_PERMISSION,
                    Permission.ADMIN_ALL_PERMISSION
            )
    ),
    MODERATOR(
            Arrays.asList(
                    Permission.MODERATOR_READ_PERMISSION,
                    Permission.MODERATOR_ALL_PERMISSION
            )
    );

    @JsonValue
    public String getName() {
        return this.name();
    }

    @JsonCreator
    public static Role fromJson(String name) {
        return Role.valueOf(name.toUpperCase());
    }

    @Getter
    private final List<Permission> permissions;
}
