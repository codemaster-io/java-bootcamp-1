package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;

import java.io.IOException;
import java.util.Map;


@SpringBootApplication(scanBasePackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        Map<String, AuthenticationProvider> providers = ctx.getBeansOfType(AuthenticationProvider.class);

        System.out.println("Registered AuthenticationProviders:");
        providers.forEach((name, provider) -> {
            System.out.println("Provider Bean Name: " + name + ", Provider Class: " + provider.getClass().getName());
        });
    }
}
