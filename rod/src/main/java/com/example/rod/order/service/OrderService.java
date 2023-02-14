package com.example.rod.order.service;


import com.example.rod.order.entity.Order;
import com.example.rod.order.entity.OrderProduct;
import com.example.rod.order.repository.OrderRepository;
import com.example.rod.product.entity.Product;
import com.example.rod.product.repository.ProductRepository;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //주문하기
    @Transactional
    public Long order(Long userId, Long productId, int count) {

        //엔티티 조회
        User user = userRepository.findByUserId(userId);
        Product product = productRepository.findProductByProductId(productId);

        //주문 상품 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, product.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(user, orderProduct);

        //주문 저장
        orderRepository.save(order);
        return order.getId();
    }

    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId) {

        //주문 엔티티 조회
        Order order = orderRepository.findByOrderId(orderId);

        //주문 취소
        order.cancel();
    }
}
