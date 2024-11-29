package com.codemaster.io;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);
    }
}