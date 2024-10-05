package io.codemaster.demo.design_patterns.proxy;

public class Main {
    public static void main(String[] args) {
        BMW bmw = new BMWProxy();
        bmw.makeSound();
    }
}
