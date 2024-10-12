package com.codemaster.io.litespring;


import com.codemaster.io.litespring.annotation.PackageScan;
import com.codemaster.io.litespring.utils.ClassScanner;
import jakarta.servlet.Filter;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class LiteSpringApplication {

    public static ApplicationContext run(Class<?> appClass) throws Exception {

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
