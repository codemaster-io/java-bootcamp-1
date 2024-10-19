package com.codemaster.io;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.Role;
import com.codemaster.io.models.User;
import com.codemaster.io.repository.ProductRepository;
import com.codemaster.io.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


@SpringBootApplication(scanBasePackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        Role role = Role.fromJson("MODERATOR");
        System.out.println("role = " + role);
        System.out.println("role.getName() = " + role.getName());

//        Map<String, AuthenticationProvider> providers = ctx.getBeansOfType(AuthenticationProvider.class);
//
//        System.out.println("Registered AuthenticationProviders:");
//        providers.forEach((name, provider) -> {
//            System.out.println("Provider Bean Name: " + name + ", Provider Class: " + provider.getClass().getName());
//        });
    }

    @Bean
    public CommandLineRunner loadData(UserRepository userRepository, ProductRepository productRepository,
                                      PasswordEncoder passwordEncoder) {
        return (args) -> {
            // Insert some sample users
            User admin = User.builder()
                    .id(1)
                    .name("Forhad Ahmed")
                    .email("forhad@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.ADMIN)
                    .permissions(Arrays.asList(Permission.ADMIN_ALL_PERMISSION))
                    .build();
            User moderator = User.builder()
                    .id(2)
                    .name("Yaseen ")
                    .email("yaseen@gmail.com")
                    .password(passwordEncoder.encode("1234"))
                    .role(Role.MODERATOR)
                    .permissions(Arrays.asList(Permission.MODERATOR_ALL_PERMISSION))
                    .build();

            userRepository.addUser(admin);
            userRepository.addUser(moderator);

            userRepository.deleteUser(moderator.getEmail());

            Product product1 = Product.builder()
                    .id(1)
                    .name("iPhone 14")
                    .price(800.0)
                    .description("A product from Apple")
                    .build();

            Product product2 = Product.builder()
                    .id(2)
                    .name("Pixel 5")
                    .price(700)
                    .description("A product from Google")
                    .build();

            // Insert some sample products
            productRepository.addProduct(product1);
            productRepository.addProduct(product2);

            productRepository.deleteProduct(1);

            // Fetch all users and products to verify
            System.out.println("Users:");
            userRepository.getUsers().forEach(user -> System.out.println(user.toString()));

            System.out.println("Products:");
            productRepository.getProducts().forEach(product -> System.out.println(product.toString()));
        };
    }
}
