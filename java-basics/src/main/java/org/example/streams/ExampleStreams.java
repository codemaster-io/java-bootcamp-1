package org.example.streams;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ExampleStreams {

    public static void main(String[] args) {
        List<Integer> numbers = new ArrayList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);
        numbers.add(10);
        numbers.add(11);
        numbers.add(9);
        numbers = numbers.stream().map(element-> {
            if (element == 10) {
                element = 12;
            }
            return element;
        }).collect(Collectors.toList());
        numbers = numbers.stream().sorted().collect(Collectors.toList());
        System.out.println(numbers);
    }
}
