package io.codemaster.demo.design_patterns.factory;

public class CarFactory {
    public Car getCar(String carType) {
        if (carType == null) {
            return null;
        }
        if (carType.equalsIgnoreCase("SEDAN")) {
            return new Sedan(); // Create and return a Sedan object
        } else if (carType.equalsIgnoreCase("SUV")) {
            return new SUV(); // Create and return an SUV object
        }
        return null; // Return null if no valid car type is found
    }
}