package com.example.rod.order.repository;

import com.example.rod.order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
@EnableJpaRepositories
public interface OrderRepository extends JpaRepository<Order, Long> {

//    Order findByOrderId(Long orderId);

    Optional<Order> findById(Long orderId);

    List<Order> findOrdersByUserId(Long userId);
}
