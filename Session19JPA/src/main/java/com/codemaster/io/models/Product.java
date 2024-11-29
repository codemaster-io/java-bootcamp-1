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
public class Product extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "products_tags",
            joinColumns = @JoinColumn(name="product_id", referencedColumnName = "id",
                    foreignKey = @ForeignKey(name = "fk_products_tags_product_id_products_id")),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id",
                    foreignKey =@ForeignKey(name = "fk_products_tags_tag_id_tags_id"))
    )
    private List<Tag> tags;

}
