package com.codemaster.io.litespring;

import com.codemaster.io.litespring.annotation.PathVariable;
import com.codemaster.io.litespring.annotation.RequestBody;
import com.codemaster.io.litespring.annotation.RequestParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
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
        dispatch(req, resp, MethodType.PUT);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp, MethodType methodType) {
        try {
            String requestUrl = req.getRequestURI();
            System.out.println("requestUrl = " + requestUrl);


            for (ControllerMethod controllerMethod : controllerMethods) {
                if (controllerMethod.getMethodType() != methodType) continue;
                String mappedUrl = controllerMethod.getUrl();
                if (!PathExtractor.isMatchUrlPattern(mappedUrl, requestUrl)) continue;

                Map<String, String> pathVariable = PathExtractor.pathVariables(mappedUrl, requestUrl);
                String body = readRequestBody(req, methodType);

                Object responseObject = invokeMethod(req, controllerMethod, pathVariable, body);

                resp.setContentType("application/json");
                resp.getWriter().write(objectMapper.writeValueAsString(responseObject));

                break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private Object invokeMethod(
            HttpServletRequest req, ControllerMethod controllerMethod,
            Map<String, String> pathVariableMap, String body) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {

        Parameter[] parameters = controllerMethod.getMethod().getParameters();
        Object[] paramObject = new Object[parameters.length];

        for(int i=0; i<parameters.length; i++) {
            if(parameters[i].isAnnotationPresent(PathVariable.class)) {
                PathVariable pathVariable = parameters[i].getAnnotation(PathVariable.class);
                paramObject[i] = pathVariableMap.get(pathVariable.value());
            }
            if(parameters[i].isAnnotationPresent(RequestBody.class)) {
                paramObject[i] = objectMapper.readValue(body, parameters[i].getType());
            }
            if(parameters[i].isAnnotationPresent(RequestParam.class)) {
                RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
                paramObject[i] = req.getParameter(requestParam.value());
            }
        }

        return controllerMethod.getMethod().invoke(controllerMethod.getInstance(), paramObject);
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
