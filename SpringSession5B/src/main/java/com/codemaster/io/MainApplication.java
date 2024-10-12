package com.codemaster.io;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;


@SpringBootApplication(scanBasePackages = {"com.codemaster.io"})
public class MainApplication {

    public static void main(String[] args) throws IOException {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);
    }
}
