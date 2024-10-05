package io.codemaster.demo.design_patterns.singleton;

public class Main {
    public static void main(String[] args) {
        Cache cache = Cache.getInstance();
        cache.putData("hello", "world");
        System.out.println("cache.getData(\"hello\") = " + cache.getData("hello"));
    }
}
