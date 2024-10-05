package org.example.lambdaexpression;

public class ExampleLambda {
    public static void main(String[] args) {

        //parameters -> statement

        Greeting greeting = (msg) -> System.out.println(msg);
        greeting.sayHello("Hello! Shamim Bhai");
    }
}


@FunctionalInterface
interface Greeting {
    void sayHello(String message);
}