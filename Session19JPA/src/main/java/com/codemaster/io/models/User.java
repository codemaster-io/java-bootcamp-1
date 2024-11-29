package com.codemaster.io.models;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {
                @UniqueConstraint(name = "uniq_email", columnNames = {"email"})},
        indexes = {
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_name", columnList = "name"),
                @Index(name = "idx_email_name", columnList = "email,name")})
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email", length = 128, unique = true, nullable = false)
    private String email;

    @Column(name = "name", length = 128, nullable = false)
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_users_address_id_addresses_id"))
    private Address address;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", length = 30)
    private Role role;
}
