package com.codemaster.io;

import jakarta.servlet.Filter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;

public class CustomTomcatServer {
    public static void main(String[] args) throws LifecycleException {
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        // Define the web application context
        // Define the web application context
        String docBase = new File(".").getAbsolutePath();
        Context context = tomcat.addContext("", docBase);

        Filter authenticationFilter = new AuthenticationFilter();
        Filter loggingFilter = new LoggingFilter();

        // Create filter definitions
        FilterDef authFilterDef = new FilterDef();
        authFilterDef.setFilter(new AuthenticationFilter());
        authFilterDef.setFilterName("AuthenticationFilter");
        context.addFilterDef(authFilterDef);

        // Create filter mappings
        FilterMap authFilterMap = new FilterMap();
        authFilterMap.setFilterName("AuthenticationFilter");
        authFilterMap.addURLPattern("/*");  // Map to all URLs
        context.addFilterMap(authFilterMap);


        FilterDef logFilterDef = new FilterDef();
        logFilterDef.setFilter(new LoggingFilter());
        logFilterDef.setFilterName("LoggingFilter");
        context.addFilterDef(logFilterDef);


        FilterMap logFilterMap = new FilterMap();
        logFilterMap.setFilterName("LoggingFilter");
        logFilterMap.addURLPattern("/*");  // Map to all URLs
        context.addFilterMap(logFilterMap);

        // Start Tomcat server
        tomcat.start();
        System.out.println("Server started at http://localhost:8080/");
        tomcat.getServer().await();
    }
}

