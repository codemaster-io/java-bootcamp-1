package com.codemaster.io.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user_table", indexes = {
        @Index(name = "idx_email", columnList = "email"),
        @Index(name = "idx_email_role", columnList = "email, user_role")
})
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 128, unique = true, nullable = false)
    private String email;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_role", length = 30)
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}
