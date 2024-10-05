package org.example.generics;

public class ExampleGenerics {

    public static void main(String[] args) {
        CustomLinkedList<Integer> customLinkedList = new CustomLinkedList<>();
        customLinkedList.add(11);
        customLinkedList.add(12);
        customLinkedList.add(13);
        customLinkedList.add(21);
        System.out.println(customLinkedList);


        CustomLinkedList<String> stringLinkedList = new CustomLinkedList<>();
        stringLinkedList.add("Yaseen");
        stringLinkedList.add("Ada");
        stringLinkedList.add("New");
        stringLinkedList.add("sdajksla");
        System.out.println(stringLinkedList);
    }


}
