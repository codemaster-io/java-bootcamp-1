package io.codemaster.demo.custom_annotation;

import java.lang.reflect.Field;

public class AnnotationProcessor {

    public static Object processAnnotations(Object obj) throws IllegalAccessException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            System.out.println("field.getName() = " + field.getName());
            // Check if the field is annotated with @CustomValue
            if (field.isAnnotationPresent(CustomValue.class)) {
                CustomValue customValue = field.getAnnotation(CustomValue.class);
                int value = customValue.value();
                int minValue = customValue.minValue();

                if(value != 0) {
                    // Set the field accessible and update its value
                    field.setAccessible(true);
                    try {
                        field.set(obj, value); // Update the original object
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }

                if(minValue != -1) {
                    field.setAccessible(true);
                    int  currentValue = (Integer) field.get(obj);
                    if (currentValue < minValue) {
                        throw new IllegalArgumentException("Field '" + field.getName() +
                                "' has a value less than the allowed minimum: " + currentValue + " < " + minValue);
                    }
                }
            }
        }
        return obj; // Return the modified original object
    }
}
