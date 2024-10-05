package io.codemaster.demo.design_patterns.proxy;

public class BMWProxy extends BMW {

    @Override
    public void makeSound() {
        System.out.println("Start here");
        super.makeSound();
        System.out.println("Stop here");
    }
}
