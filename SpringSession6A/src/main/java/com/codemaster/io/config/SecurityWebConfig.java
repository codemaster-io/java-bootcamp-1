package com.codemaster.io.config;

import com.codemaster.io.filters.JwtAuthFilter;
import com.codemaster.io.provider.CustomAuthenticationProvider;
import com.codemaster.io.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;


@Component
@EnableWebSecurity(debug = true)
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) // Enable method-level security
public class SecurityWebConfig {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        daoAuthenticationProvider.setUserDetailsService(userService);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.authorizeHttpRequests()
//                .antMatchers("/api/products").authenticated()
//                .antMatchers("/api/users").authenticated()
//                .antMatchers("/api/auth").permitAll()
//                .antMatchers("/").permitAll()
                .anyRequest().permitAll();

//        httpSecurity.httpBasic();
        httpSecurity.csrf().disable();
        httpSecurity.cors().disable();
//        httpSecurity.formLogin();

//        httpSecurity.csrf().ignoringAntMatchers("/api/*");
//        httpSecurity.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
//        httpSecurity.authenticationProvider(authenticationProvider());
//        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        httpSecurity.exceptionHandling().authenticationEntryPoint();
//        httpSecurity.exceptionHandling().accessDeniedHandler();

//        httpSecurity.formLogin()
//                .loginPage("/loginpage");


        return httpSecurity.build();
    }
}
