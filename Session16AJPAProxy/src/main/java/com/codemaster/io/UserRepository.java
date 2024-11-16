package com.codemaster.io;

public interface UserRepository {
    void insert(User user);
    User get(int id);
    User get(String name);
}
