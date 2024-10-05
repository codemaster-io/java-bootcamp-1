package org.example;

public class Bicycle implements MotorizedVehicle, PaddledVehicle {

    int wheelCount;
    String manufacturer;

    int acceleration;

    @Override
    public void start(String procedure) {
        System.out.println(procedure);
        acceleration++;
    }

    @Override
    public void print() {
        MotorizedVehicle.super.print();
    }

    @Override
    public int add(int a, int b) {
        return a+b;
    }

    public int add(int... arr) {
        int sum = 0;
        for (int number: arr) {
            sum += number;
        }
        return sum;
    }

}
