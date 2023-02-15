//package com.example.rod.order.entity;
//
//import com.example.rod.user.entity.User;
//import lombok.*;
//import org.springframework.format.annotation.DateTimeFormat;
//
//import javax.persistence.*;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//
//@Entity
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Table(name = "orders")
//public class Order extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long Id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    private User user; //주문 회원
//
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    private LocalDateTime orderDate; //주문 날짜
//
//    @Enumerated(EnumType.STRING)
//    private OrderStatus orderStatus; //주문 상태 [ORDER, CANCEL]
//
//    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    private List<OrderProduct> orderProducts = new ArrayList<>();
//
//    @Builder
//    public Order(User user, LocalDateTime orderDate, OrderStatus orderStatus) {
//        this.user = user;
//        this.orderDate = orderDate;
//        this.orderStatus = orderStatus;
//    }
//
//    public void addOrderProduct(OrderProduct orderProduct) {
//        orderProducts.add(orderProduct);
//        orderProduct.setOrder(this);
//    }
//
//    public static Order createOrder(User user, List<OrderProduct> orderProductList) {
//
//        Order order = Order.builder()
//                .user(user)
//                .orderStatus(OrderStatus.ORDER)
//                .orderDate(LocalDateTime.now())
//                .build();
//
//        for (OrderProduct orderProduct : orderProductList) {
//            order.addOrderProduct(orderProduct);
//        }
//
//        return order;
//    }
//
//    public int getTotalPrice() {
//        int totalPrice = 0;
//        for (OrderProduct orderProduct : orderProducts) {
//            totalPrice += orderProduct.getTotalPrice();
//        }
//
//        return totalPrice;
//    }
//
//    //주문 취소 시 상품 재고 증가/ 주문 상태 취소로 변환
//    public void cancelOrder() {
//        this.orderStatus = OrderStatus.CANCEL;
//
//        for (OrderProduct orderProduct : orderProducts) {
//            orderProduct.cancel();
//        }
//    }
//
//
//
//
//
//
//
//
//
//
////    //==연관관계 메서드==//
////    public void setUser(User user) {
////        this.user = user;
////        user.getOrders().add(this);
////    }
////    public void addOrderProduct(OrderProduct orderProduct) {
////        orderProducts.add(orderProduct);
////        orderProduct.setOrder(this);
////    }
////
////    //생성 메서드
////    public static Order createOrder(User user, OrderProduct... orderProducts) {
////        Order order = new Order();
////        order.setUser(user);
////        for (OrderProduct orderproduct : orderProducts) {
////            order.addOrderProduct(orderproduct);
////        }
////        order.setStatus(OrderStatus.ORDER);
////        order.setOrderDate(LocalDateTime.now());
////        return order;
////    }
////
////    //비즈니스 로직
////    //주문 취소
////    public void cancel() {
////        this.setStatus(OrderStatus.CANCEL);
////        for (OrderProduct orderProduct : orderProducts) {
////            orderProduct.cancel();
////        }
////    }
//
//
//
//}
