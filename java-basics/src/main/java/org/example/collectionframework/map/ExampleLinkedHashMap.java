package org.example.collectionframework.map;

import java.util.LinkedHashMap;

public class ExampleLinkedHashMap {

    public static void main(String[] args) {
        LinkedHashMap<Integer, String> professions = new LinkedHashMap<>();
        professions.put(1, "Software Engineer");
        professions.put(2, "Project Manager");
        System.out.println(professions);
    }
}
