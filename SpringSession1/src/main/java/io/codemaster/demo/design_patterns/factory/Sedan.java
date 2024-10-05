package io.codemaster.demo.design_patterns.factory;


public class Sedan implements Car {
    @Override
    public void drive() {
        System.out.println("Driving a Sedan");
    }
}
