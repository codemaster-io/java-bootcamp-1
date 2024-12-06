package com.codemaster.io;

import com.codemaster.io.models.*;
import com.codemaster.io.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Override
    public void run(String... args) throws Exception {

        User user1 = User.builder()
                .id(1)
                .name("john")
                .email("john@gmail.com")
                .role(Role.ADMIN)
                .address(Address.builder()
                        .road("123 Main Street")
                        .city("NY")
                        .phone("12345677")
                        .build())
                .build();

        userService.saveUser(user1);
        user1 = userService.getUser(user1.getId());
        System.out.println("user1 = " + user1);

        User user2 = User.builder()
                .id(2)
                .name("Doe Smith")
                .email("smith@gmail.com")
                .role(Role.CUSTOMER)
                .address(Address.builder()
                        .road("456 Golden Street")
                        .city("CA")
                        .phone("3457834")
                        .build())
                .build();

        userService.saveUser(user2);
        user2 = userService.getUser(user2.getId());
        System.out.println("user2 = " + user2);

        //
//        System.out.println("user1 = " + user1);
//
//        Tag tag1 = Tag.builder()
//                .id("electronics")
//                .displayNameEN("Electronics")
//                .build();
//
//        Tag tag2 = Tag.builder()
//                .id("phone")
//                .displayNameEN("Phone")
//                .build();
//
//        Tag tag3 = Tag.builder()
//                .id("laptop")
//                .displayNameEN("Laptop")
//                .build();
//
//        tagRepository.saveAll(Arrays.asList(tag1, tag2, tag3));
//
//
//        Product product1 = Product.builder()
//                .title("iPhone 14")
//                .description("Apple product")
//                .price(1100)
//                .tags(Arrays.asList(
//                        Tag.builder().id("phone").build(),
//                        Tag.builder().id("electronics").build()
//                ))
//                .build();
//        productRepository.save(product1);
//
//
//        Product product2 = Product.builder()
//                .title("Lenovo Laptop")
//                .description("High end gaming laptop")
//                .price(1400)
//                .tags(Arrays.asList(
//                        Tag.builder().id("laptop").build(),
//                        Tag.builder().id("electronics").build()
//                ))
//                .build();
//        productRepository.save(product2);
//
//        Order order1 = Order.builder()
//                .name("Phone Order")
//                .totalAmount(1600)
//                .user(User.builder().id(user1.getId()).build())
//                .orderStatus(OrderStatus.IN_PROGRESS)
//                .build();
//
//        orderRepository.save(order1);
//
//        Order order2 = Order.builder()
//                .name("Laptop Order")
//                .totalAmount(2200)
//                .user(User.builder().id(user1.getId()).build())
//                .orderStatus(OrderStatus.IN_PROGRESS)
//                .build();
//        orderRepository.save(order2);
//
//
//        Pageable pageable = PageRequest.of(1, 1, Sort.by("id").descending());
//
//        List<Order> orders = orderRepository.findOrderByStatus(OrderStatus.IN_PROGRESS, pageable);
//        System.out.println("orders = " + orders);
//
//        List<Product> products = productRepository.findProductsByTag("phone");
//        System.out.println("products = " + products);
//
//        List<String> productIds = productRepository.findProductIdsByTag("phone");
//        System.out.println("productIds = " + productIds);

    }
}
