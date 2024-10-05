package org.example.generics;


public class CustomLinkedList<Something> implements CustomList<Something> {

    private CustomLinkedList<Something> next;
    private Something element;

    private int size = 0;


    @Override
    public void add(Something element) {
        size++;
        CustomLinkedList<Something> iterator = this;
        while (iterator.next != null) {
            iterator = iterator.next;
        }
        iterator.element = element;
        iterator.next = new CustomLinkedList<>();
    }

    @Override
    public Something get(int index) {
        CustomLinkedList<Something> it = this;
        int counter = 0;
        while (it != null) {
            if (counter == index) {
                return it.element;
            }
            counter++;
            it = it.next;
        }
        return null;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        String result = "";
        CustomLinkedList<Something> iterator = this;
        while (iterator != null) {
            if (iterator.element != null) {
                result = result.concat(String.valueOf(iterator.element));
                result = result.concat(", ");
            }
            iterator = iterator.next;
        }

        return result;
    }
}
