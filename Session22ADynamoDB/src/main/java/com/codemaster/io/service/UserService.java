package com.codemaster.io.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.codemaster.io.models.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final DynamoDBMapper dynamoDBMapper;

    public UserService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveUser(User user) {
        dynamoDBMapper.save(user);
    }

    public User getUser(int id) {
        return dynamoDBMapper.load(User.class, id);
    }

    public void deleteUser(int id) {
        User user = new User();
        user.setId(id);
        dynamoDBMapper.delete(user);
    }

    public void updateUser(int id , int newAge) {
        User user = getUser(id);
        if (user != null) {
            user.setAge(newAge);
            dynamoDBMapper.save(user);
        }
    }

    public List<User> findUsersByRole(String role) {
        DynamoDBQueryExpression<User> queryExpression = new DynamoDBQueryExpression<User>()
                .withIndexName("RoleIndex")  // Specify the GSI name for role-based query
                .withConsistentRead(false)   // Use eventually consistent reads for GSI
                .withHashKeyValues(new User())
                .withRangeKeyCondition("roleForGSI", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withS(role)));

        return dynamoDBMapper.query(User.class, queryExpression);
    }
}

