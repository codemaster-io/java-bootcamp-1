package com.codemaster.io.repository;

import com.codemaster.io.models.Order;
import com.codemaster.io.models.OrderStatus;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query("select o from Order o where o.orderStatus = :orderStatus")
    List<Order> findOrderByStatus(OrderStatus orderStatus, Pageable pageable);
}
