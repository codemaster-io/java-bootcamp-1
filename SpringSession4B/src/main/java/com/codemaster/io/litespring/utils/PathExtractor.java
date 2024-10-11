package com.codemaster.io.litespring.utils;

import java.util.HashMap;
import java.util.Map;

public class PathExtractor {
    public static boolean isMatchUrlPath(String mappedUrl, String requestUrl) {
        String[] mappedParts = mappedUrl.split("/");
        String[] requestParts = requestUrl.split("/");

        if (mappedParts.length != requestParts.length) {
            return false;
        }

        for (int i = 0; i<mappedParts.length; i++) {
            if (mappedParts[i].startsWith("{") && mappedParts[i].endsWith("}")) continue;
            if (!mappedParts[i].equals(requestParts[i])) return false;
        }

        return true;
    }

    public static Map<String, String> pathVariables(String mappedUrl, String requestUrl) {
        String[] mappedParts = mappedUrl.split("/");
        String[] requestParts = requestUrl.split("/");

        Map<String, String> pathVariables = new HashMap<>();

        for (int i = 0; i < mappedParts.length; i++) {
            if (mappedParts[i].startsWith("{") && mappedParts[i].endsWith("}")) {
                String variableName = mappedParts[i].substring(1, mappedParts[i].length() - 1);
                pathVariables.put(variableName, requestParts[i]);
            }
        }
        return pathVariables;
    }
}
