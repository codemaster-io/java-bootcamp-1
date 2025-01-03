package com.codemaster.io;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.Role;
import com.codemaster.io.models.User;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.io.IOException;
import java.util.List;


@SpringBootApplication(scanBasePackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

    }

    @Bean
    public CommandLineRunner loadData(UserService userService, ProductService productService) {
        return (args) -> {
            // Insert some sample users
            User admin = User.builder()
                    .name("Admin User")
                    .email("admin@gmail.com")
                    .password("1234")
                    .role(Role.ADMIN)
                    .permissions(List.of(Permission.ADMIN_ALL_PERMISSION))
                    .build();
            User moderator = User.builder()
                    .name("Moderator User ")
                    .email("moderator@gmail.com")
                    .password("1234")
                    .role(Role.MODERATOR)
                    .permissions(List.of(Permission.MODERATOR_ALL_PERMISSION))
                    .build();

            userService.addUser(admin);
            Thread.sleep(100);
            userService.addUser(moderator);
            Thread.sleep(100);

            Product product1 = Product.builder()
                    .name("iPhone 14")
                    .price(800.0)
                    .description("A product from Apple")
                    .createdByUserEmail(admin.getEmail())
                    .build();

            Product product2 = Product.builder()
                    .name("Pixel 5")
                    .price(700)
                    .description("A product from Google")
                    .createdByUserEmail(moderator.getEmail())
                    .build();

            // Insert some sample products
            productService.addProduct(product1);
            Thread.sleep(100);
            productService.addProduct(product2);
            Thread.sleep(100);


            // Fetch all users and products to verify
            System.out.println("Users:");
            userService.getAllUsers().forEach(user -> System.out.println(user.toString()));

            System.out.println("Products:");
            productService.getAllProducts().forEach(product -> System.out.println(product.toString()));
        };
    }
}
