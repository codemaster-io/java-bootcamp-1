package com.codemaster.io.litespring;

import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class TomCatConfig {
    private static Tomcat tomcat;
    private static Context context;

    private static final String contextPath = "" ;

    protected static void registerServlet(Object instance, Class<?> clazz, String urlMapping) {
        tomcat.addServlet(contextPath, clazz.getSimpleName(), (HttpServlet) instance);
        context.addServletMappingDecoded(urlMapping, clazz.getSimpleName());
    }

    protected static void initTomcat() throws LifecycleException {
        tomcat = new Tomcat();
        tomcat.setPort(8080);
        tomcat.getConnector();

        // Create a host
        Host host = tomcat.getHost();
        host.setName("localhost");
        host.setAppBase("webapps");

        tomcat.start();

        String docBase = new File(".").getAbsolutePath();
        context = tomcat.addContext(contextPath, docBase);
    }
}
