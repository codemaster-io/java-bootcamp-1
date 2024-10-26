package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {ServletWebServerFactoryAutoConfiguration.class})
public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApplication.class);
    }
}