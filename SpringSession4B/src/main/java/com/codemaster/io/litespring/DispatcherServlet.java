package com.codemaster.io.litespring;

import com.codemaster.io.litespring.annotation.PathVariable;
import com.codemaster.io.litespring.annotation.RequestBody;
import com.codemaster.io.litespring.annotation.RequestParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Parameter;
import java.util.List;
import java.util.Map;

public class DispatcherServlet extends HttpServlet {
    private final List<ControllerMethod> controllerMethods;
    private ObjectMapper objectMapper;

    public DispatcherServlet(List<ControllerMethod> controllerMethods) {
        this.controllerMethods = controllerMethods;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, MethodType.GET);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, MethodType.POST);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp, MethodType.POST);
    }

    private void dispatch(HttpServletRequest request, HttpServletResponse response, MethodType methodType) throws ServletException, IOException {
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        String requestPath = request.getRequestURI();

        for(ControllerMethod controllerMethod : controllerMethods) {

            if(!controllerMethod.getMethodType().equals(methodType)) continue;

            if(!PathExtractor.isMatchUrlPath(controllerMethod.getMappedUrl(), requestPath)) continue;

            Map<String, String> pathVariables = PathExtractor.pathVariables(controllerMethod.getMappedUrl(), requestPath);

            String body = readRequestBody(request, methodType);

            Object responseObject = invokeMethod(request, controllerMethod, pathVariables, body);

            response.setContentType("application/json");
            String jsonResponse = objectMapper.writeValueAsString(responseObject);
            response.getWriter().write(jsonResponse);
        }
    }



    private Object invokeMethod(
            HttpServletRequest req, ControllerMethod controllerMethod,
            Map<String, String> pathVariables, String body) {
        try {
            Parameter[] parameters = controllerMethod.getMethod().getParameters();
            Object[] paramObjects = new Object[parameters.length];

            for (int i = 0; i < parameters.length; i++) {
                if (parameters[i].isAnnotationPresent(PathVariable.class)) {
                    PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
                    String varName = pathVariable.value();
                    paramObjects[i] = pathVariables.get(varName);
                }
                if(parameters[i].isAnnotationPresent(RequestParam.class)) {
                    RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                    paramObjects[i] = req.getParameter(requestParam.value());
                }
                if(parameters[i].isAnnotationPresent(RequestBody.class)) {
                    paramObjects[i] = objectMapper.readValue(body, parameters[i].getType());
                }
            }
            return controllerMethod.getMethod().invoke(controllerMethod.getInstance(), paramObjects);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readRequestBody(HttpServletRequest request, MethodType methodType) {
        if(methodType == MethodType.POST || methodType == MethodType.PUT) {
            try {
                BufferedReader reader = request.getReader();
                StringBuilder body = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    body.append(line); // Read body content
                }
                return body.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
