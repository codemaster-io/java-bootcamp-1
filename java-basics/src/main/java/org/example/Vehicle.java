package org.example;

public interface Vehicle {

    void start(String procedure);

    default void brakes() {

    }
}
