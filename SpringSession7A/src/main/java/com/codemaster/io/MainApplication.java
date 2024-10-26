package com.codemaster.io;

import com.codemaster.io.models.Permission;
import com.codemaster.io.models.Product;
import com.codemaster.io.models.Role;
import com.codemaster.io.models.User;
import com.codemaster.io.service.AuthService;
import com.codemaster.io.service.GoogleAPIService;
import com.codemaster.io.service.ProductService;
import com.codemaster.io.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@SpringBootApplication(scanBasePackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        Map<String, AuthenticationProvider> providers = ctx.getBeansOfType(AuthenticationProvider.class);

        System.out.println("Registered AuthenticationProviders:");
        providers.forEach((name, provider) -> {
            System.out.println("Provider Bean Name: " + name + ", Provider Class: " + provider.getClass().getName());
        });
    }

    @Bean
    public CommandLineRunner loadData(UserService userService, ProductService productService,
                                      AuthService authService, GoogleAPIService googleAPIService,
                                      AuthenticationManager authenticationManager) {
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
                    .addedByUserEmail(admin.getEmail())
                    .build();

            Product product2 = Product.builder()
                    .name("Pixel 5")
                    .price(700)
                    .description("A product from Google")
                    .addedByUserEmail(moderator.getEmail())
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


//            String code = "4/0AVG7fiSr126A9r1Pun2Hz2PBCWcuFzFfv-scLOEwf54y23_xhtR3CMLLV9TocpSl3eyhVA";
//            googleOAuthService.getAccessToken(code);
//


        };
    }
}
