package com.codemaster.io;


import com.codemaster.io.models.*;
import com.codemaster.io.repositories.OrderRepository;
import com.codemaster.io.repositories.ProductRepository;
import com.codemaster.io.repositories.TagRepository;
import com.codemaster.io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {


        User user1 = User.builder()
                .name("John")
                .email("john@gmail.com")
                .address(Address.builder()
                        .address("123 Main Street, NY")
                        .phoneNumber("123456789")
                        .build())
                .role(Role.ADMIN)
                .build();
        user1 = userRepository.save(user1);

        System.out.println("user1 = " + user1);

        User user2 = User.builder()
                .name("Alex")
                .email("alex@gmail.com")
                .address(Address.builder()
                        .address("456 Wall Street, WN")
                        .phoneNumber("472220000")
                        .build())
                .role(Role.ADMIN)
                .build();
        user2 = userRepository.save(user2);
        System.out.println("user2 = " + user2);

        // Add Tags
        Tag tag1 = new Tag("electronics", "Electronics");
        Tag tag2 = new Tag("laptop", "Laptop");
        Tag tag3 = new Tag("phone", "Phone");
        Tag tag4 = new Tag("clothing", "Clothing");

        tagRepository.saveAll(Arrays.asList(tag1, tag2, tag3, tag4));

        Product product1 = Product.builder()
                .title("Lenovo Laptop")
                .tags(Arrays.asList(
                        Tag.builder().id("electronics").build(),
                        Tag.builder().id("laptop").build()))
                .description("High end gaming laptop")
                .build();

        product1 = productRepository.save(product1);
        System.out.println("product1 = " + product1);

        Product product2 = Product.builder()
                .title("iPhone 14")
                .tags(Arrays.asList(
                        Tag.builder().id("electronics").build(),
                        Tag.builder().id("phone").build()))
                .description("An Apple product")
                .build();
        product2 = productRepository.save(product2);
        System.out.println("product2 = " + product2);

        Order order1 = Order.builder()
               .description("Order for laptop")
               .amount(1500)
               .status(OrderStatus.IN_PROGRESS)
               .user(User.builder().id(user1.getId()).build())
               .build();
        orderRepository.save(order1);

        Order order2 = Order.builder()
                .description("Order for iPhone")
                .amount(1100)
                .status(OrderStatus.IN_PROGRESS)
                .user(User.builder().id(user2.getId()).build())
                .build();
        orderRepository.save(order2);

        List<Order> orders = orderRepository.findOrdersAboveAmount(100);
        System.out.println("orders = " + orders);

        int pageNumber = 0;
        int size = 10;
        Pageable pageable = PageRequest.of(pageNumber, size,
                Sort.by("createdAt").ascending());

        List<String> orderIds = orderRepository.findOrderIdsByStatus(
                OrderStatus.IN_PROGRESS, pageable);
        System.out.println("orderIds = " + orderIds);


        List<Product> products = productRepository.findProductsByTagIds(
                Arrays.asList("electronics", "phones"));
        System.out.println("products = " + products);

        List<Order> inProgressOrders = orderRepository.findOrdersByStatus(OrderStatus.IN_PROGRESS);
        System.out.println("inProgressOrders = " + inProgressOrders);

        order2 = order2.toBuilder()
                .status(OrderStatus.COMPLETED)
                .build();
        order2 = orderRepository.save(order2);
        List<Order> completedOrders = orderRepository.findOrdersByStatus(OrderStatus.COMPLETED);
        System.out.println("completedOrders = " + completedOrders);



    }
}

