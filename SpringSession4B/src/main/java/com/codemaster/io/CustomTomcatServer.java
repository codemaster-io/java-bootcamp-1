package com.codemaster.io;

import com.codemaster.io.filters.AuthenticationFilter;
import com.codemaster.io.filters.LoggingFilter;
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



        // Start Tomcat server
        tomcat.start();
        System.out.println("Server started at http://localhost:8080/");
        tomcat.getServer().await();
    }
}

