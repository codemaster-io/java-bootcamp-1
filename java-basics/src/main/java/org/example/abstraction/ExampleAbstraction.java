package org.example.abstraction;

public class ExampleAbstraction {

    public static void main(String[] args) {
        AbstractCard abstractCard = new AbstractCardImpl();
        System.out.println(abstractCard.addAllCard());
    }
}
