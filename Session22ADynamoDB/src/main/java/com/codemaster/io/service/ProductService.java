package com.codemaster.io.service;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;
import com.codemaster.io.models.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductService {

    private final DynamoDBMapper dynamoDBMapper;

    public ProductService(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    public void saveProduct(Product product) {
        dynamoDBMapper.save(product);
    }

    public Product getProduct(String productId) {
        return dynamoDBMapper.load(Product.class, productId);
    }

    public void deleteProduct(int productId) {
        Product product = new Product();
        product.setId(productId);
        dynamoDBMapper.delete(product);
    }

    public void updateProduct(String productId, double newPrice) {
        Product product = getProduct(productId);
        if (product != null) {
            product.setPrice(newPrice);
            dynamoDBMapper.save(product);
        }
    }

    public List<Product> findProductsByPrice(Double price) {
        DynamoDBQueryExpression<Product> queryExpression = new DynamoDBQueryExpression<Product>()
                .withIndexName("PriceIndex")  // Specify the GSI name
                .withConsistentRead(false)    // Use eventually consistent reads for GSI
                .withHashKeyValues(new Product())
                .withRangeKeyCondition("priceForGSI", new Condition()
                        .withComparisonOperator(ComparisonOperator.EQ)
                        .withAttributeValueList(new AttributeValue().withN(price.toString())));

        return dynamoDBMapper.query(Product.class, queryExpression);
    }

    public List<Product> scanProductsByDescription(String description) {
        DynamoDBScanExpression scanExpression = new DynamoDBScanExpression()
                .withFilterExpression("contains(description, :desc)")  // Filtering by description
                .withExpressionAttributeValues(Map.of(":desc", new AttributeValue().withS(description)));

        return dynamoDBMapper.scan(Product.class, scanExpression);
    }
}
