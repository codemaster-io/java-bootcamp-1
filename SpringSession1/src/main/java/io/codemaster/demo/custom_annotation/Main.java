package io.codemaster.demo.custom_annotation;

import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws IllegalAccessException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException {

        Class<?> customerClass = Class.forName("io.codemaster.demo.custom_annotation.Customer");
        String className = customerClass.getName();
        System.out.println("className = " + className);
        Object customerInstance =  customerClass.getDeclaredConstructor().newInstance();
        customerClass.getMethod("setName", String.class).invoke(customerInstance, "John");
        customerClass.getMethod("setBalance", int.class).invoke(customerInstance, 10);


        System.out.println("customerInstance = " + customerInstance);


        Customer customer = new Customer("Shakil", 10);

        System.out.println("customer = " + customer);

        // Process annotations to modify the original object
        Customer updatedCustomer = (Customer) AnnotationProcessor.processAnnotations(customer);

        System.out.println("updatedCustomer = " + updatedCustomer);


        Product product = new Product("iPhone 16", 11);

        Product updatedProduct = (Product) AnnotationProcessor.processAnnotations(product);
        System.out.println("updatedProduct = " + updatedProduct);
    }
}
