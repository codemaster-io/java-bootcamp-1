package org.example.annotations;

import java.lang.reflect.Field;

public class ExampleAnnotations {

    public static void main(String[] args) throws IllegalAccessException {
        User user = new User();
        if (user.getClass().isAnnotationPresent(CustomSetter.class)) {
            Field[] fields = user.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getName().equals("age")) {
                    System.out.println("Age detected...");
                    field.setAccessible(true);
                    field.set(user, 30);
                }
            }
        }
        CustomValidator.validate(user);
    }
}
