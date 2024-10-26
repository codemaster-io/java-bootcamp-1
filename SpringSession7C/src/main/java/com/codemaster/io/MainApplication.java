package com.codemaster.io;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationProvider;

import java.util.Map;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        Map<String, AuthenticationProvider> providers = ctx.getBeansOfType(AuthenticationProvider.class);

        System.out.println("Registered AuthenticationProviders:");
        providers.forEach((name, provider) -> {
            System.out.println("Provider Bean Name: " + name + ", Provider Class: " + provider.getClass().getName());
        });
    }
}