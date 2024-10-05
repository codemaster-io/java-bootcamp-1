package org.example;

public interface MotorizedVehicle extends Vehicle {
    default void print() {
        System.out.println("It's a motorized vehicle.");
    }

    int add(int a, int b);
}


// base entity
// user
//