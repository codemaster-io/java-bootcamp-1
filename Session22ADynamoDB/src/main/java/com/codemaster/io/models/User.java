package com.codemaster.io.models;

import com.amazonaws.services.dynamodbv2.datamodeling.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@DynamoDBTable(tableName = "User")
public class User {

    @DynamoDBHashKey(attributeName = "user_id")
    private Integer id;

    private String email;

    private String name;

    @DynamoDBTypeConvertedEnum
    private Role role;

    private Integer age;

    private Address address;
}
