package com.codemaster.io.models;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@RequiredArgsConstructor
public enum Role {

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

    @Getter
    private final List<Permission> permissions;
}
