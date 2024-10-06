package com.codemaster.io.litespring;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.litespring.annotation.PackageScan;
import com.codemaster.io.litespring.annotation.Servlet;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;

public class ApplicationFactory {
    private static final Map<Class<?>, Object> beanClassMap = new HashMap<>();
    private static final Map<String, Object> beanNameMap = new HashMap<>();

    private static final Class<?>[] validAnnotators =
            { Component.class, Servlet.class, Autowired.class, PackageScan.class };

    public static void start(Class<?> appClass) throws Exception {

        PackageScan scanPackage = appClass.getAnnotation(PackageScan.class);

        List<Class<?>> classes = ClassScanner.scanAllClasses(
                new ArrayList<>(Arrays.asList(scanPackage.value())), validAnnotators);

        beanCreates(classes);
        injectDependencies(classes);

        TomCatConfig.initTomcat();
        registerServlets(classes);

    }

    private static void beanCreates(List<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Servlet.class)) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                beanClassMap.put(clazz, instance);
                beanNameMap.put(clazz.getName(), instance);
            }
        }
    }

    private static void registerServlets(List<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Servlet.class)) {
                Servlet servlet = clazz.getAnnotation(Servlet.class);
                Object instance = beanClassMap.get(clazz);
                TomCatConfig.registerServlet(instance, clazz, servlet.urlMapping());
            }
        }
    }

    private static void injectDependencies(List<Class<?>> classes) throws Exception {
        for(Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Servlet.class)) {

                Object bean = beanClassMap.get(clazz);

                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Object instance = beanClassMap.get(field.getType());
                        field.setAccessible(true);
                        field.set(bean, instance);
                    }
                }
            }
        }
    }



    public static Object getBean(Class<?> clazz) {
        return beanClassMap.get(clazz);
    }

    public static Object getBean(String name) {
        return beanNameMap.get(name);
    }
}
