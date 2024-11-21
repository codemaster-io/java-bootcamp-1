package com.codemaster.io.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;

    @ManyToMany
    @JoinTable(
            name = "products_tags", // intermediate joining table
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_products_tags_product_id_products_id")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id", foreignKey =@ForeignKey(name = "fk_products_tags_tag_id_tags_id"))
    )
    private List<Tag> tags;

}
