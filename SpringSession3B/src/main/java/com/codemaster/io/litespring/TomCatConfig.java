package com.codemaster.io.litespring;

import com.codemaster.io.litespring.annotation.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class TomCatConfig {
    private static Tomcat tomcat;
    private static Context context;
    private static final String contextPath = "" ;

    public TomCatConfig(int port) {
        initTomcat(port);
    }

    private void initTomcat(int port) {
        try {
            tomcat = new Tomcat();
            tomcat.setPort(port);
            tomcat.getConnector();

            // Create a host
            Host host = tomcat.getHost();
            host.setName("localhost");
            host.setAppBase("webapps");

            tomcat.start();

            String docBase = new File(".").getAbsolutePath();
            context = tomcat.addContext(contextPath, docBase);
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    protected void registerServlet(Object instance, String className, String urlMapping) {
        tomcat.addServlet(contextPath, className, (HttpServlet) instance);
        context.addServletMappingDecoded(urlMapping, className);
    }







    private static void registerServlet(Class<?> clazz, String urlMapping) throws Exception {
        if (clazz.isAnnotationPresent(RestController.class)) {
            for (Method method : clazz.getDeclaredMethods()) {
                // Register a controller's method as a servlet for URL mapping
                Tomcat tomcat = new Tomcat();
                tomcat.addServlet("", clazz.getSimpleName(), new HttpServlet() {
                    @Override
                    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
                        try {
                            handleControllerPostRequest(req, resp, clazz, method);
                        } catch (Exception e) {
                            throw new IOException(e);
                        }
                    }
                });
                tomcat.addContext("", null).addServletMappingDecoded(urlMapping, clazz.getSimpleName());
        }
        }
    }

    private static void handleControllerPostRequest(HttpServletRequest req, HttpServletResponse resp, Class<?> clazz, Method method) throws Exception {
        // TODO
        Object controller = null;


        // Extract @RequestBody parameter
        Object requestBody = null;
        Parameter[] parameters = method.getParameters();
        for (int i = 0; i < parameters.length; i++) {
            if (parameters[i].isAnnotationPresent(RequestBody.class)) {
                // Convert JSON body to Java object using a library like Jackson
                requestBody = new ObjectMapper().readValue(req.getReader(), parameters[i].getType());
            }
        }

        // Call the method with @RequestBody parameter
        Object returnValue = method.invoke(controller, requestBody);

        // Convert return object to JSON and write to the response if @ResponseBody is present
        String jsonResponse = new ObjectMapper().writeValueAsString(returnValue);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse);
    }




    private static void handleControllerGetRequest(
            HttpServletRequest req, HttpServletResponse resp, Class<?> clazz,
            Method method, String urlMapping) throws Exception {
        // TODO
        Object controller = null;


        // Extract path variables and request params
        Object[] args = resolveMethodArguments(req, method, urlMapping);

        // Call the method
        Object returnValue = method.invoke(controller, args);

        String jsonResponse = new ObjectMapper().writeValueAsString(returnValue);
        resp.setContentType("application/json");
        resp.getWriter().write(jsonResponse);
    }

    private static Object[] resolveMethodArguments(
            HttpServletRequest req, Method method, String urlMapping) throws Exception {
        Parameter[] parameters = method.getParameters();
        Object[] args = new Object[parameters.length];

        String path = req.getRequestURI().replace(req.getContextPath(), "");
        String[] urlParts = urlMapping.split("/");
        String[] pathParts = path.split("/");

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(PathVariable.class)) {
                // Extract path variable
                PathVariable pathVar = parameter.getAnnotation(PathVariable.class);
                String varName = pathVar.value();
                args[i] = extractPathVariable(varName, urlParts, pathParts);
            }

            if (parameter.isAnnotationPresent(RequestParam.class)) {
                // Extract request param
                RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                String paramName = requestParam.value();
                args[i] = req.getParameter(paramName);
            }
        }
        return args;
    }

    private static String extractPathVariable(String varName, String[] urlParts, String[] pathParts) {
        // Logic to map the path variable from the URL pattern to the actual path
        for (int j = 0; j < urlParts.length; j++) {
            if (urlParts[j].equals("{" + varName + "}")) {
                return pathParts[j];
            }
        }
        return null;
    }

}
