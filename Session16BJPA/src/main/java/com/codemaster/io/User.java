package com.codemaster.io;

import lombok.*;

import javax.persistence.*;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String name;

    @OneToOne(cascade = CascadeType.ALL)  // Cascade all operations (insert, update, delete)
    @JoinColumn(name = "role_id", referencedColumnName = "role_id")  // Foreign key column for Role
    private Role role;

}
