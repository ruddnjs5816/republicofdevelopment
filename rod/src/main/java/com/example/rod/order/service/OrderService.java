package com.example.rod.order.service;


import com.example.rod.order.dto.OrderDto;
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
import javax.persistence.EntityNotFoundException;


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
    public Long order(OrderDto orderDto, String username) {

        //주문할 상품 조회
        Product product = productRepository.findById(orderDto.getProductId()).orElseThrow(EntityNotFoundException::new);
        //회원의 username을 이용해서 회원정보 조회
        Optional<User> user = userRepository.findByUsername(username);

        List<OrderProduct> orderProductList = new ArrayList<>();
        //주문할 상품 엔티티와 주문 수량을 이용 -> 주문 상품 엔티티 생성
        OrderProduct orderProduct = OrderProduct.createOrderProduct(product, orderDto.getCount());
        orderProductList.add(orderProduct);

        Order order = Order.createOrder(user.get(), orderProductList);
        orderRepository.save(order);

        return order.getId();
    }



    //주문 취소

    //로그인한 사용자와 주문데이터를 생성한 사용자가 동일인인지 확인
    @Transactional(readOnly = true)
    public boolean validateOrder(Long orderId, String username) {

        Optional<User> curUser = userRepository.findByUsername(username);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        User savedUser = order.getUser();

        if (!curUser.get().getUsername().equals(savedUser.getUsername())) {
            return false;
        }

        return true;
    }
    @Transactional
    public void cancelOrder(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();

    }
}
