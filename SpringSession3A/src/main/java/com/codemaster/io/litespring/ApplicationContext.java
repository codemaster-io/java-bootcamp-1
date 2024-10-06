package com.codemaster.io.litespring;

import com.codemaster.io.litespring.annotation.Autowired;
import com.codemaster.io.litespring.annotation.Component;
import com.codemaster.io.litespring.annotation.Servlet;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationContext {
    private final Map<String, Object> beanFactory = new HashMap<>();
    private final int TOMCAT_PORT = 8080;
    private TomCatConfig tomCatConfig;


    private static ApplicationContext applicationContext;

    private ApplicationContext() {
        tomCatConfig = new TomCatConfig(TOMCAT_PORT);
    }

    public static synchronized ApplicationContext getInstance() {
        if(applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    protected void createSpringContainer(List<Class<?>> classes) {
        try {
            beanCreates(classes);
            injectDependencies(classes);
            registerServlets(classes);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    protected void beanCreates(List<Class<?>> classes) throws Exception {
        for (Class<?> clazz : classes) {
            if (clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Servlet.class)) {
                Object instance = clazz.getDeclaredConstructor().newInstance();
                beanFactory.put(clazz.getSimpleName(), instance);
            }
        }
    }

    protected void registerServlets(List<Class<?>> classes) {
        for (Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Servlet.class)) {
                Servlet servlet = clazz.getAnnotation(Servlet.class);
                Object instance = beanFactory.get(clazz.getSimpleName());
                tomCatConfig.registerServlet(instance, clazz.getSimpleName(), servlet.urlMapping());
            }
        }
    }

    protected void injectDependencies(List<Class<?>> classes) throws IllegalAccessException {
        for(Class<?> clazz : classes) {
            if(clazz.isAnnotationPresent(Component.class) ||
                    clazz.isAnnotationPresent(Servlet.class)) {

                Object bean = beanFactory.get(clazz.getSimpleName());

                Field[] fields = bean.getClass().getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Object instance = beanFactory.get(field.getType().getSimpleName());
                        field.setAccessible(true);
                        field.set(bean, instance);
                    }
                }
            }
        }
    }

    public Object getBean(Class<?> clazz) {
        return beanFactory.get(clazz.getSimpleName());
    }

    public Object getBean(String name) {
        return beanFactory.get(name);
    }
}
