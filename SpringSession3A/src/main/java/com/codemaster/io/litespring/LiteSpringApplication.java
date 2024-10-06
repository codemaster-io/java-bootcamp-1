package com.codemaster.io.litespring;


import com.codemaster.io.litespring.annotation.PackageScan;

import java.io.File;
import java.net.URL;
import java.util.*;

public class LiteSpringApplication {

    public static ApplicationContext start(Class<?> appClass) throws Exception {

        ApplicationContext applicationContext = ApplicationContext.getInstance();

        PackageScan packageScan = appClass.getAnnotation(PackageScan.class);
        ClassLoader classLoader = LiteSpringApplication.class.getClassLoader();

        List<Class<?>> classes = new ArrayList<>();

        for(String packageName : packageScan.scanPackages()) {
            URL resource = classLoader.getResource(packageName.replace('.', '/'));
            classes.addAll(ClassScanner.scan(new File(resource.getPath()), packageName));
        }

        applicationContext.createSpringContainer(classes);

        return applicationContext;
    }
}
