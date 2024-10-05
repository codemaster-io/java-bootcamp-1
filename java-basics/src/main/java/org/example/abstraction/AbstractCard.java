package org.example.abstraction;

public abstract class AbstractCard {
    public boolean addAllCard() {
        System.out.println("Inside... [ AbstractCard::addAllCard ]");
        System.out.println(addCard());
        return true;
    }
    public boolean addCard() {
        throw new RuntimeException("");
    }
}
