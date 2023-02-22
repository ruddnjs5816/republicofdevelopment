package com.example.rod.order.repository;

import com.example.rod.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {


    Optional<Order> findById(Long orderId);

//    List<Order> findAllByUser(Long userId);
}
