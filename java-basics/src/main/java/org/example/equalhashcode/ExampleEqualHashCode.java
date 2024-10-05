package org.example.equalhashcode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

//@EqualsAndHashCode
class Person {
    String name;
    String email;
    int age;

    Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) return false;
        if (object.getClass() != this.getClass()) return false;
        Person person = (Person) object;
        return person.name.equals(this.name) && person.age == this.age && this.email.equals(person.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, email, age);
    }
}

public class ExampleEqualHashCode {

    public static void main(String[] args) {
        Set<Person> personSet = new HashSet<>();
        personSet.add(new Person("Yaseen", 32, "easin@innospace.com"));
        personSet.add(new Person("Forhad", 35, "forhad@bkash.com"));
        System.out.println(personSet.contains(new Person("Yaseen", 32, "easin@innospace.com")));
    }
}
