package com.codemaster.io;

import com.codemaster.io.models.User;
import com.codemaster.io.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class MainApplication {
    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(MainApplication.class);
    }

    @Bean
    CommandLineRunner run(UserService userService) {
        return args -> {
            // Create and add a user
            User user = User.builder()
                    .name("user5")
                    .email("user5@gmail.com")
                    .age(32)
                    .country("BD")
                    .build();
            int id = userService.addUserAndReturnId(user);
            System.out.println("id  = " + id);

            user = user.toBuilder()
                    .id(id)
                    .name("userUpdated")
                    .build();
            boolean chk = userService.updateUser(user);
            System.out.println("UpdateStatus  = " + chk);

            if(chk) {
                chk = userService.deleteUser(user.getId());
                System.out.println("Delete Status = " + chk);

                chk = userService.addUserAndAuditData(user);
                System.out.println("Audit status = " + chk);

                List<User> users = userService.getListUser();
                System.out.println("users = " + users);
            }
        };
    }
}