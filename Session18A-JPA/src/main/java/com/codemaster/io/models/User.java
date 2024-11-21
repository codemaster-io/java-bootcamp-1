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
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(128) NOT NULL")
    private String email;

    @Column(columnDefinition = "VARCHAR(128) NOT NULL DEFAULT 'Unknown'")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", foreignKey = @ForeignKey(name = "fk_users_address_id_addresses_id"))
    private Address address;

    @OneToMany(mappedBy = "userId", fetch = FetchType.EAGER)
//    @ToString.Exclude
    private List<Order> orders;

    @Transient // ignoring to writing in the DB
    private int orderCount;
}
