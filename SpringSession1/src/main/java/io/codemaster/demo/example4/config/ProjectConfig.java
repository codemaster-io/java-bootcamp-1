package io.codemaster.demo.example4.config;

import io.codemaster.demo.example4.repository.ProductRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ProjectConfig {

    @Bean(name = "InMemoryRepository")
    @Primary
    public ProductRepository InMemoryRepository() {
        System.out.println("productRepository call.");
        return new ProductRepository();
    }

    @Bean(name = "DBRepository")
    public ProductRepository dbRepository() {
        System.out.println("productRepository call.");
        return new ProductRepository();
    }
}
