package org.example.abstraction;

public class AbstractCardImpl extends AbstractCard {

    public boolean addAllCard() {
        super.addAllCard();
        return true;
    }

    public boolean addCard() {
        System.out.println("Inside... [ AbstractCardImpl::addCard ]");
        return true;
    }
}
