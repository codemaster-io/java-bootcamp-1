package com.codemaster.io.repositories;

import com.codemaster.io.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Find users by name
    List<User> findByName(String name);

    // Custom query to count users with orders
//    @Query("SELECT u FROM User u WHERE u.orders IS NOT EMPTY")
//    List<User> findUsersWithOrders();
}