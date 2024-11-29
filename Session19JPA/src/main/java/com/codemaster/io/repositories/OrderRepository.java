package com.codemaster.io.repositories;

import com.codemaster.io.models.Order;
import com.codemaster.io.models.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("SELECT o FROM Order o WHERE o.amount > :amount")
    List<Order> findOrdersAboveAmount(double amount);

    @Query("SELECT o FROM Order o WHERE o.status = :orderStatus")
    List<Order> findOrdersByStatus(OrderStatus orderStatus);

    @Query("SELECT o.id FROM Order o WHERE o.status = :orderStatus")
    List<String> findOrderIdsByStatus(OrderStatus orderStatus, Pageable pageable);
}