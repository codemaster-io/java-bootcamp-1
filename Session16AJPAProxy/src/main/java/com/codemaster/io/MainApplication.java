package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        JdbcTemplate jdbcTemplate = ctx.getBean(JdbcTemplate.class);

        UserRepository userRepository = (UserRepository) JPAProxy.createProxy(UserRepository.class, User.class, jdbcTemplate);
        userRepository.insert(User.builder()
                .id(1)
                .name("Admin")
                .build());

        User user1 = userRepository.get(1);
        User user2 = userRepository.get("Admin");

        System.out.println("user1 = " + user1);
        System.out.println("user2 = " + user2);

        ProductRepository productRepository = (ProductRepository) JPAProxy.createProxy(ProductRepository.class, Product.class, jdbcTemplate);
        productRepository.insert(Product.builder()
                .id(1)
                .title("iPhone")
                .build());

        Product product1 = productRepository.get(1);
        Product product2 = productRepository.get("iPhone");

        System.out.println("product1 = " + product1);
        System.out.println("product2 = " + product2);

    }
}