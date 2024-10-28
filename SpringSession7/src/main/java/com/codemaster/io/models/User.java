package com.codemaster.io.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class User {
    private long id;

    private String name;

    private String email;

    private String imageUrl;

    @ToString.Exclude
    @JsonIgnore
    private String password;

    private Role role;

    private List<Permission> permissions;
}
