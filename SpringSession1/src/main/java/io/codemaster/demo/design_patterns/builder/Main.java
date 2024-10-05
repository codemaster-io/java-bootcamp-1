package io.codemaster.demo.design_patterns.builder;

public class Main {
    public static void main(String[] args) {

        Product product = new Product.ProductBuilder()
                .name("test").build();

        System.out.println("product = " + product.getName());
    }
}
