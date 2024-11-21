package com.codemaster.io;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {

    public int capacity;
    public int ttl;

    public Map<String, KVNode> map;

    private KVNode head;
    private KVNode tail;

    public LRUCache(int capacity, int ttl) {
        this.capacity = capacity;
        map = new HashMap<>();

        head = new KVNode("", "");
        tail = new KVNode("", "");

        tail.next = head;
        head.prev = tail;
    }

    public void remove(KVNode kvNode) {
        kvNode.prev.next = kvNode.next;
        kvNode.next.prev = kvNode.prev;
    }

    public void moveToFront(KVNode kvNode) {
        head.prev.next = kvNode;
        kvNode.prev = head.prev;
        head.prev = kvNode;
        kvNode.next = head;
    }

    public void put(String key, String value) {
        if(map.containsKey(key)) {
            KVNode keyNode = map.get(key);
            keyNode.value = value;
            remove(keyNode);
            moveToFront(keyNode);
        } else {
            if(map.size() >= capacity) {
                KVNode kvNode = tail.next;
                remove(tail.next);
                map.remove(kvNode.key);
            }
            KVNode kvNode = new KVNode(key, value);
            moveToFront(kvNode);
            map.put(key, kvNode);
        }
    }

    public String get(String key) {
        if(!map.containsKey(key)) return null;

        KVNode kvNode = map.get(key);
        remove(kvNode);
        moveToFront(kvNode);

        return kvNode.value;
    }

    public static void main(String[] args) {

        LRUCache lruCache = new LRUCache(2, 3600);

        lruCache.put("1", "Java Tutorial");
        lruCache.put("2", "Golang Tutorial");
        String value = lruCache.get("1");
        System.out.println("value = " + value);
        lruCache.put("3", "Python Tutorial");
        value = lruCache.get("2");
        System.out.println("value = " + value);
        value = lruCache.get("3");
        System.out.println("value = " + value);

    }
}
