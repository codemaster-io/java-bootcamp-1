package io.codemaster.demo.example3;


import io.codemaster.demo.example3.models.Product;
import io.codemaster.demo.example3.service.ProductService;
import io.codemaster.demo.example3.service.SearchService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class MainApplication {

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(MainApplication.class);

        for(String bean : ctx.getBeanDefinitionNames()) {
            System.out.println("bean = " + bean);
        }

        ProductService productService = ctx.getBean(ProductService.class);
        SearchService searchService = ctx.getBean(SearchService.class);

        Product product1 = Product.builder()
                .name("iPhone 16")
                .build();
        String id1 = productService.addProduct(product1);
        System.out.println("id1 = " + id1);

        Product product2 = Product.builder()
                .name("Pixel 5")
                .build();
        String id2 = productService.addProduct(product2);
        System.out.println("id2 = " + id2);

        Product product3 = Product.builder()
                .name("iPhone 15")
                .build();
        String id3 = productService.addProduct(product3);
        System.out.println("id3 = " + id3);

        List<Product> filterResults = searchService.search("iphone");
        for(Product product : filterResults) {
            System.out.println("product = " + product);
        }
    }
}
