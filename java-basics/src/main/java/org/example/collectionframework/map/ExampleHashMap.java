package org.example.collectionframework.map;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.TreeMap;
import java.util.TreeSet;


public class ExampleHashMap {

    public static void main(String[] args) {
        TreeMap<Integer, String> hashMap = new TreeMap<>();
        hashMap.put(1, "Yaseen");
        hashMap.put(2, "Shimul");
        hashMap.put(50, "Nahian");
        hashMap.put(5, "Asad");
        hashMap.put(11, "Arafat");
        hashMap.put(19, "X");

        System.out.println(hashMap);
    }
}
