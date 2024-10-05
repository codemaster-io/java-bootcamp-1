package io.codemaster.demo.design_patterns.factory;

public class Main {
    public static void main(String[] args) {
        CarFactory carFactory = new CarFactory();

        // Create a Sedan
        Car car1 = carFactory.getCar("SEDAN");
        car1.drive(); // Output: Driving a Sedan

        // Create an SUV
        Car car2 = carFactory.getCar("SUV");
        car2.drive(); // Output: Driving an SUV
    }
}