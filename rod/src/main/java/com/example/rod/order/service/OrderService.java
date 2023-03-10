package com.example.rod.order.service;


import com.example.rod.order.dto.OrderResponseDto;
import com.example.rod.order.entity.Order;
import com.example.rod.order.entity.OrderStatus;
import com.example.rod.order.repository.OrderRepository;
import com.example.rod.product.entity.Product;
import com.example.rod.product.repository.ProductRepository;
import com.example.rod.security.details.UserDetailsImpl;
import com.example.rod.user.entity.User;
import com.example.rod.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    //주문하기
    @Transactional
    public void order(Long productId, UserDetailsImpl userDetails) {

        //주문할 상품 조회
        Product product = productRepository.findById(productId).orElseThrow(EntityNotFoundException::new);

        User user = userRepository.findByUserId(userDetails.getUserId());

//        Integer caculatePoint = (user.getPoint()) - (product.getPrice());
//        user.setPoint(caculatePoint);



        //결제
        user.payPoint(product.getPrice());
        Order order = new Order
                (userDetails.getUser(), product.getProductId(), product.getPrice(), product.getProductDescription(),
                        product.getProductName(), OrderStatus.ORDER);
        orderRepository.save(order);

    }



    //주문 취소
    @Transactional
    public void cancelOrder(Long orderId, User user) {

        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new IllegalArgumentException("주문이 없습니다"));

        order.cancelOrder();

        Product product = productRepository.findById(order.getProductId()).orElseThrow(() ->
                new IllegalArgumentException("상품이 없습니다"));


        if(order.getOrderStatus() == OrderStatus.CANCEL){
            user.cancelPoint(product.getPrice());

            userRepository.save(user);

            System.out.println("product :"+ product.getPrice());

        }else {
            new IllegalArgumentException("이미 취소된 상품입니다.");
        }

    }

    public List<OrderResponseDto> getMyOrders(User user) {

        List<Order> byUser = orderRepository.findByUser(user);
//        OrderResponseDto orderResponseDto =
//                new OrderResponseDto(byUser.getProductName(), byUser.getPrice(), byUser.getProductDescription());

        List<OrderResponseDto> resultData = new ArrayList<>();

        for(Order order : byUser){
            resultData.add(new OrderResponseDto(order));
        }

        return resultData;
    }




}
