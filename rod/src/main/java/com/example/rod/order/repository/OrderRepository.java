package com.example.rod.order.repository;

import com.example.rod.order.entity.Order;
import com.example.rod.user.entity.User;
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

//    Page<Order> findByUser(User user, Pageable pageable);

//    List<Order> getOrdersById (User user);

    List<Order> findByUser(User user);

//    List<Order> findAllByUser(Long userId);
}
