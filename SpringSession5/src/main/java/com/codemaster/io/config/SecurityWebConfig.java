package com.codemaster.io.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity(debug = true)
public class SecurityWebConfig {
    @Bean
    InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        GrantedAuthority authority = new SimpleGrantedAuthority("ADMIN");


        UserDetails user1 = User.withUsername("forhad")
                .password("test")
                .roles("ADMIN")
                .build();

        InMemoryUserDetailsManager inMemoryDB = new InMemoryUserDetailsManager(user1);

        return inMemoryDB;
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests()
                .antMatchers("/api/products").permitAll()
                .antMatchers("/api/search").permitAll()
                .antMatchers("/api/login").authenticated()
                .anyRequest().permitAll();

        httpSecurity.csrf().ignoringAntMatchers("/api/*");

        httpSecurity.formLogin()
                .loginPage("/loginpage");


        return httpSecurity.build();
    }
}
