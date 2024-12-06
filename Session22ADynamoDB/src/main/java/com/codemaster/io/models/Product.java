package com.codemaster.io.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBVersionAttribute;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Product")
public class Product {
    @DynamoDBHashKey(attributeName = "id") // Partition Key
    private Integer id;

    private String title;

    private String description;
    private double price;

    private List<Tag> tags;

    @DynamoDBVersionAttribute
    private Long version; // Optimistic locking version field
}
