package com.codemaster.io;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Optional;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        User user = User.builder()
                .id(1)
                .name("Admin")
                .build();

        UserRepository userRepository = ctx.getBean(UserRepository.class);

        userRepository.save(user);
        Optional<User> user1 = userRepository.findByName("Admin");
        System.out.println("user1 = " + user1);

        ProductRepository productRepository = ctx.getBean(ProductRepository.class);

        Product product = Product.builder()
                .id(1)
                .title("iPhone")
                .build();
        productRepository.save(product);
        Optional<Product> product1 = productRepository.findByTitle("iphone");
        System.out.println("product1 = " + product1);
    }
}