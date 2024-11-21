package com.codemaster.io.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tags")
public class Tag {
    @Id
    private String id;

    private String name;

//    @ManyToMany(mappedBy = "tags")
//    private List<Product> products;
}
