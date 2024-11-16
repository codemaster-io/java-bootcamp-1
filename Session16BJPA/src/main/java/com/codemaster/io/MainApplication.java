package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Optional;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        UserRepository userRepository = ctx.getBean(UserRepository.class);
        userRepository.save(User.builder()
                .id(2)
                .name("Admin2")
                .build());

        Optional<User> user1 = userRepository.findByName("Admin2");
        System.out.println(user1.orElse(User.builder().build()));
//        Optional<User> user2 = userRepository.find("Admin");

//        System.out.println("user2 = " + user2);

        ProductRepository productRepository = ctx.getBean(ProductRepository.class);
        productRepository.save(Product.builder()
                .id(2)
                .title("iPhone2")
                .build());

        Optional<Product> product1 = productRepository.findByTitle("iPhone2");
        System.out.println(product1.orElse(Product.builder().build()));
//        Product product2 = productRepository.get("iPhone");

        System.out.println("product1 = " + product1);
//        System.out.println("product2 = " + product2);

    }
}