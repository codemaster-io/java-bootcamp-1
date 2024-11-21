package com.codemaster.io.services;

import com.codemaster.io.models.Order;
import com.codemaster.io.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

//     Find orders by user ID
//    public List<Order> findOrdersByUser(Integer userId) {
//        return orderRepository.findOrdersByUserId(userId);
//    }

    // Find orders above a certain amount
    public List<Order> findOrdersAboveAmount(double amount) {
        return orderRepository.findOrdersAboveAmount(amount);
    }

    // Save an order
    public Order saveOrder(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    public Optional<Order> findOrder(Integer id) {
        return orderRepository.findById(id);
    }

}

