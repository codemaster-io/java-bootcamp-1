package org.example.serialization;

import lombok.ToString;

import java.io.*;

public class ExampleSerializable {

    public static void main(String[] args) throws FileNotFoundException {
        User user = new User("Easin", 32);
        try(FileOutputStream fos = new FileOutputStream("inp.ser")) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fos);
            objectOutputStream.writeObject(user);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try(FileInputStream fis = new FileInputStream("inp.ser")) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fis);
            User user1 = (User) objectInputStream.readObject();
            System.out.println(user1);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}


@ToString
class User implements Serializable {
    String name;
    int age;

    User(String name, int age) {
        this.name = name;
        this.age = age;
    }
}