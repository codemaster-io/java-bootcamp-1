package org.example.collectionframework.set;

import java.util.TreeSet;

public class ExampleTreeSet {

    public static void main(String[] args) {
        TreeSet<Integer> treeSet = new TreeSet<>();
        treeSet.add(12);
        treeSet.add(1);
        treeSet.add(10);
        treeSet.add(20);
        System.out.println(treeSet);
        System.out.println("Higher value of 13 is: " + treeSet.higher(10));
        System.out.println("Lower value of 13 is: " + treeSet.lower(10));
        System.out.println("Ceiling value of 11 is: " + treeSet.ceiling(11));
        System.out.println("Floor value of 11 is: " + treeSet.floor(10));
    }
}
