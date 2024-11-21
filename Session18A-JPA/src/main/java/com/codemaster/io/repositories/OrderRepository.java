package com.codemaster.io.repositories;

import com.codemaster.io.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    // Custom query to get orders by user id
//    @Query("SELECT o FROM Order o WHERE o.user.id = :userId")
//    List<Order> findOrdersByUserId(Integer userId);

    // Custom query to get orders above a certain amount
    @Query("SELECT o FROM Order o WHERE o.amount > :amount")
    List<Order> findOrdersAboveAmount(double amount);
}