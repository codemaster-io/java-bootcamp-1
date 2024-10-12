package com.codemaster.io.litespring.context;

public class UserContext {
    private static ThreadLocal<String> userThreadLocal = new ThreadLocal<>();

    public static void setUserContext(String username) {
        userThreadLocal.set(username);
    }

    public static String getUserContext() {
        return userThreadLocal.get();
    }

    public static void clear() {
        userThreadLocal.remove();
    }
}
