package com.codemaster.io.config;

import com.codemaster.io.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // Enable method-level security
public class SecurityWebConfig {

    @Bean
    UserDetailsService inMemoryUserDetailsManager() {

        User user = new User("user", "1234",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_USER")));

        User admin = new User("admin", "1234",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ADMIN:ALL_PERMISSION")));

        User moderator = new User("moderator", "1234",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_MODERATOR")));

        User adminViewer = new User("admin_viewer", "1234",
                Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"),
                        new SimpleGrantedAuthority("ADMIN:READ_PERMISSION")));

        InMemoryUserDetailsManager inMemoryDB = new InMemoryUserDetailsManager(
                user, admin, moderator, adminViewer);

        return inMemoryDB;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests()
                .anyRequest().authenticated();

        httpSecurity.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf().disable();
        httpSecurity.formLogin(Customizer.withDefaults());
        httpSecurity.oauth2Login();

        return httpSecurity.build();
    }
}
