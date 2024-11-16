package com.codemaster.io;

public interface ProductRepository {
    void insert(Product product);
    Product get(int id);
    Product get(String title);
}
