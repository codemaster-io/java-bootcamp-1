package com.codemaster.io.config;

import com.codemaster.io.handler.CustomAccessDeniedHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // Enable method-level security
public class SecurityWebConfig {

    @Bean
    UserDetailsService inMemoryUserDetailsManager() {

        UserDetails user = User.withUsername("user")
                .password("1234")
                .roles("USER")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("1234")
                .authorities("ROLE_ADMIN", "ADMIN:ALL_PERMISSION")
                .build();

        UserDetails moderator = User.withUsername("moderator")
                .password("1234")
                .roles("MODERATOR")
                .build();

        UserDetails adminViewer = User.withUsername("admin_viewer")
                .password("1234")
                .authorities("ROLE_ADMIN", "ADMIN:READ_PERMISSION")
                .build();

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
                .antMatchers("/dashboard").authenticated()
                .antMatchers("/admin").authenticated()
                .antMatchers("/user").authenticated()
                .anyRequest().permitAll();

        httpSecurity.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.csrf().disable();
        httpSecurity.formLogin(Customizer.withDefaults());

        return httpSecurity.build();
    }
}
