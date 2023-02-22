package com.example.rod.order.service;


import com.example.rod.order.dto.OrderDto;
import com.example.rod.order.dto.OrderResponseDto;
import com.example.rod.order.entity.Order;
import com.example.rod.order.repository.OrderRepository;
import com.example.rod.product.dto.ProductResponseDto;
import com.example.rod.product.entity.Product;
import com.example.rod.product.repository.ProductRepository;
import com.example.rod.security.exception.CustomException;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //주문하기
    @Transactional
    public void order(OrderDto orderDto, Long userId) {

        //주문할 상품 조회
        Product product = productRepository.findById(orderDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        User user = userRepository.findByUserId(userId);

        //결제
        user.payPoint(product.getPrice());
        Order order = new Order(userId, product.getProductId());
        orderRepository.save(order);

    }



    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId, Long userId) {

        Order order = orderRepository.findById(orderId).orElseThrow(() -> new IllegalArgumentException("주문이 없습니다"));
        User user = userRepository.findById(order.getUserId()).orElseThrow(() -> new IllegalArgumentException("유저가 없습니다"));
        Product product = productRepository.findById(order.getProductId()).orElseThrow(() -> new IllegalArgumentException("상품이 없습니다"));

        order.cancelOrder();
        user.refundPoint(product.getPrice());
        orderRepository.save(order);
        userRepository.save(user);

    }

    public List<OrderResponseDto> getMyOrders(Long userId) {
        List<Order> orderList = orderRepository.findAllByUser(userId);
        List<OrderResponseDto> result = new ArrayList<>();

        for (Order order: orderList) {
            OrderResponseDto dto = new OrderResponseDto(order.getId(), order.getProductId(), order.getOrderDate(), order.getOrderStatus());
            result.add(dto);
        }
        return result;

    }

}
