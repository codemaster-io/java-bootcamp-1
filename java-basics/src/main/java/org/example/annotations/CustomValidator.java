package org.example.annotations;

import java.lang.reflect.Field;

public class CustomValidator {

    public static <T> void validate(T property) throws IllegalAccessException {
        Field[] fields = property.getClass().getDeclaredFields();
        for (Field field: fields) {
            if (field.isAnnotationPresent(ValidAge.class)) {
                ValidAge validAge = field.getAnnotation(ValidAge.class);
                field.setAccessible(true);
                var value = field.get(property);
                if (value instanceof Integer providedValue) {
                    int minAge = validAge.min();
                    int maxAge = validAge.max();
                    if (providedValue < minAge || providedValue > maxAge) {
                        throw new RuntimeException(validAge.message());
                    }
                }
            }
        }
    }
}
