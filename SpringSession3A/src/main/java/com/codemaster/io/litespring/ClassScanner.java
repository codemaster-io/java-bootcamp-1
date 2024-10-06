package com.codemaster.io.litespring;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class ClassScanner {
    public static List<Class<?>> scanAllClasses(
            List<String> packages, Class<?>[] filteredAnnotations) throws ClassNotFoundException {

        List<Class<?>> filteredClasses = new ArrayList<>();

        for (String pkg : packages) {
            List<Class<?>> classes = find(pkg);

            for (Class<?> clazz : classes) {
                if (hasAnyValidAnnotation(clazz, filteredAnnotations)) {
                    filteredClasses.add(clazz);
                }
            }
        }
        return filteredClasses;
    }

    private static boolean hasAnyValidAnnotation(Class<?> clazz, Class<?>[] validAnnotations) {
        for (Class<?> validAnnotation : validAnnotations) {
            if (clazz.isAnnotationPresent(validAnnotation.asSubclass(Annotation.class))) {
                return true;
            }
        }
        return false;
    }

    public static List<Class<?>> find(String packageName) throws ClassNotFoundException {
        String path = packageName.replace(".", "/");
        File directory = new File(ClassLoader.getSystemClassLoader().getResource(path).getFile());
        return findClassesInDirectory(directory, packageName);
    }

    private static List<Class<?>> findClassesInDirectory(File directory, String packageName) throws ClassNotFoundException {
        List<Class<?>> classes = new ArrayList<>();
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                classes.addAll(findClassesInDirectory(file, packageName + "." + file.getName()));
            } else if (file.getName().endsWith(".class")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                classes.add(Class.forName(className));
            }
        }
        return classes;
    }
}

