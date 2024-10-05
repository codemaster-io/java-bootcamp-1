package org.example.collectionframework.set;

import java.util.HashSet;
import java.util.Spliterator;

public class ExampleHashSet {

    public static void main(String[] args) {
        HashSet<Integer> numbers = new HashSet<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(5);
        numbers.add(100);
        numbers.add(54);
        numbers.add(2);
        boolean exist = numbers.contains(100);
        System.out.println(numbers);
    }
}
