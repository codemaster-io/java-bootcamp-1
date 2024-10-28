package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class MainApplication {
    public static void main(String[] args) {
        DataSourceAutoConfiguration dataSourceAutoConfiguration;

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);
    }
}