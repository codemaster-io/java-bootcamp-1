package com.codemaster.io.litespring;

import jakarta.servlet.Filter;
import jakarta.servlet.http.HttpServlet;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.util.descriptor.web.FilterDef;
import org.apache.tomcat.util.descriptor.web.FilterMap;

import java.io.File;
import java.util.List;

public class TomCatConfig {
    private static Tomcat tomcat;
    private static Context context;
    private static final String contextPath = "" ;

    public TomCatConfig(int port) {
        initTomcat(port);
    }

    private void initTomcat(int port) {
        tomcat = new Tomcat();
        tomcat.setPort(port);
        tomcat.getConnector();
        String docBase = new File(".").getAbsolutePath();
        context = tomcat.addContext(contextPath, docBase);
    }

    protected void start() {
        try {
            // Create a host
            Host host = tomcat.getHost();
            host.setName("localhost");
            host.setAppBase("webapps");
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    protected void registerServlet(Object instance, String className, String urlMapping) {
        tomcat.addServlet(contextPath, className, (HttpServlet) instance);
        context.addServletMappingDecoded(urlMapping, className);
    }

    protected void addFilters(List<Filter> filters) {
        for(Filter filter : filters) {
            FilterDef filterDef = new FilterDef();
            filterDef.setFilter(filter);
            filterDef.setFilterName(filter.getClass().getSimpleName());
            context.addFilterDef(filterDef);


            // Create filter mappings
            FilterMap filterMap = new FilterMap();
            filterMap.setFilterName(filter.getClass().getSimpleName());
            filterMap.addURLPattern("/*");
            context.addFilterMap(filterMap);
        }
    }
}
