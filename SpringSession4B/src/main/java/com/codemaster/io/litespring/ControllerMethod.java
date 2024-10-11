package com.codemaster.io.litespring;

import com.codemaster.io.litespring.enums.MethodType;
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
