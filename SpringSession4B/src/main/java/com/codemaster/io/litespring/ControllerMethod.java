package com.codemaster.io.litespring;

import lombok.Builder;
import lombok.Data;

import java.lang.reflect.Method;

@Builder
@Data
public class ControllerMethod {

    private Class<?> clz;
    private Method method;
    private String mappedUrl;
    private MethodType methodType;

    private Object instance;
}
