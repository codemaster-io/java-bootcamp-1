package com.codemaster.io.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "TEXT")
    private String description;

    private double amount;

//    @ManyToOne(cascade = CascadeType.MERGE)
//    @JoinColumn(name = "user_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_orders_user_id_users_id"))
//    private User user;

    private Integer userId;
}
