package com.codemaster.io;

public class KVNode {
    public String key;
    public String value;

    public long timestamp;

    public KVNode prev, next;

    public KVNode(String key, String value) {
        this.key = key;
        this.value = value;
        timestamp = System.currentTimeMillis();
    }
}
