package org.example.generics;

public interface CustomList<Something> {
    void add(Something element);
    Something get(int index);
    int size();
}
