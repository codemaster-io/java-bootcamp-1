package com.codemaster.io;

import com.codemaster.io.models.Address;
import com.codemaster.io.models.Order;
import com.codemaster.io.models.User;
import com.codemaster.io.services.OrderService;
import com.codemaster.io.services.ProductService;
import com.codemaster.io.services.TagService;
import com.codemaster.io.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private TagService tagService;

    @Autowired
    private OrderService orderService;

    @Override
    public void run(String... args) throws Exception {

//         Create Users
        Address address1 = Address.builder()
                .address("123 Main St")
                .phoneNumber("555555")
                .build();

        User user1 = User.builder()
                .name("User1")
                .email("user1@gmail.com")
                .address(address1)
                .build();

        user1 = userService.saveUser(user1);
        System.out.println("user1 = " + user1);



        Order order1 = Order.builder()
                .description("test order")
                .userId(user1.getId())
//                .user(User.builder()
//                        .id(user1.getId())
//                        .build())
                .amount(10)
                .build();
        order1 = orderService.saveOrder(order1);
        System.out.println("order1 = " + order1);

//        System.out.println("order1.getUser() = " + order1.getUser());
//        System.out.println(orderService.findOrder(order1.getId()));

        Optional<User> user2 = userService.findUser(user1.getId());
//        System.out.println("user2 = " + user2);
//        List<Order> orderList = user2.get().getOrders();
//        System.out.println("orderList = " + orderList);
//        System.out.println("user2.orElseThrow() = " + user2.orElse(null));

//        List<Order> orders = orderService.findOrdersByUser(user1.getId());
//        System.out.println("orders = " + orders);




        /*
        UserAddress address2 = new UserAddress(null, "456 Oak St", "555-5678", null);
        User user2 = new User(null, "user2@example.com", "User Two", address2, null, 0);
        address2.setUser(user2);
        userService.saveUser(user2);

        // Create Tags
        Tag tag1 = new Tag("T001", "Electronics", null);
        Tag tag2 = new Tag("T002", "Clothing", null);
        tagService.saveTag(tag1);
        tagService.saveTag(tag2);

        // Create Products and link with tags
        Product product1 = new Product(1, "Laptop", Arrays.asList(tag1));
        Product product2 = new Product(2, "Shirt", Arrays.asList(tag2));
        productService.saveProduct(product1);
        productService.saveProduct(product2);

        // Create Orders
        Order order1 = new Order(0, "Laptop Order", 1000.0, user1);
        Order order2 = new Order(0, "Shirt Order", 50.0, user2);
        orderService.saveOrder(order1);
        orderService.saveOrder(order2);

        System.out.println("Sample data loaded!");


         */

    }
}

