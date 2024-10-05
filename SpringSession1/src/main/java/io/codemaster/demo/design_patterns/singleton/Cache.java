package io.codemaster.demo.design_patterns.singleton;

import java.util.HashMap;
import java.util.Map;

public class Cache {
    private Map<String, String> map;

    private static Cache instance;

    private Cache() {
        map = new HashMap<>();
    }

    public static Cache getInstance() {
        if(instance == null) {
            instance = new Cache();
        }
        return instance;
    }

    public boolean putData(String key, String value) {
        map.put(key, value);
        return true;
    }

    public String getData(String key) {
        return map.get(key);
    }
}
