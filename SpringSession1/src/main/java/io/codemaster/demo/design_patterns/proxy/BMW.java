package io.codemaster.demo.design_patterns.proxy;

public class BMW implements Car{
    @Override
    public void makeSound() {
        System.out.println("BMW Sound");
    }
}
