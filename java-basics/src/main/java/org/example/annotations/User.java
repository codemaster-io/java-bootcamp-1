package org.example.annotations;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@CustomSetter
@ToString
public class User {

    private String name;

    private String email;

    @ValidAge(min = 24, max = 32)
    private final int age = 20;

    public int getAge() {
        return age;
    }
}
