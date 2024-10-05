package io.codemaster.demo.design_patterns.factory;

public class SUV implements Car {
    @Override
    public void drive() {
        System.out.println("Driving an SUV");
    }
}