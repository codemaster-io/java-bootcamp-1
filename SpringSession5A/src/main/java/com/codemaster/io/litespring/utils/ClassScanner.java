package com.codemaster.io.litespring.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {
    public static List<Class<?>> scan(File dir, String packageName) throws ClassNotFoundException {
        List<Class<?>> classList = new ArrayList<>();

        for(File file : dir.listFiles()) {
            if(file.isFile() && file.getName().contains(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");

                classList.add(Class.forName(className));
            }
            else classList.addAll(scan(file, packageName + "." + file.getName()));
        }
        return classList;
    }

}

