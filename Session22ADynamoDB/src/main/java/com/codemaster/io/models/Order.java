package com.codemaster.io.models;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBRangeKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "Order")
public class Order {
    @DynamoDBHashKey(attributeName = "user_id")
    private Integer user_id;

    @DynamoDBRangeKey(attributeName = "order_id")
    private Integer order_id;

    private String name;
    private double totalAmount;

    private OrderStatus orderStatus;

    @DynamoDBAttribute(attributeName = "order_date")
    private String date;

}
