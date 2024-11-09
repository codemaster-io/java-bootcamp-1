package com.codemaster.io;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class);
    }

    @Bean
    CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            User user = User.builder()
                    .name("user30ajldjfljaldjflajdfljasdlfjl")
                    .email("user30@gmail.com")
                    .age(30)
                    .country("BD")
                    .build();

            userRepository.insertUserAndAuditData(user);

        };
    }
}
