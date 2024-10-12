package com.codemaster.io.litespring.annotation;

import com.codemaster.io.litespring.enums.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMapping {
    String url() default "";
    MethodType type() default MethodType.GET; // Default to GET if not specified
}
