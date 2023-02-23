/*
//package com.example.rod.order.entity;
//
//import com.example.rod.product.entity.Product;
//import com.example.rod.share.TimeStamped;
//import com.example.rod.user.entity.User;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import net.bytebuddy.implementation.bind.annotation.BindingPriority;
//
//import javax.persistence.*;
//
//@Entity
//@Getter @Setter
//@NoArgsConstructor
//@Table(name = "order_product")
//public class OrderProduct extends TimeStamped {
//
//    @Id @GeneratedValue
//    @Column(name = "order_product_id")
//    private Long Id;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id")
//    private Product product; //주문 상품
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "order_id")
//    private Order order; //주문
//    private int orderPrice; //주문 가격
//
//
//    @Builder
//    public OrderProduct(Product product, Order order, int orderPrice) {
//        this.product = product;
//        this.order = order;
//        this.orderPrice = orderPrice;
//    }
//
//    public void setOrder(Order order) {this.order = order;}
//
//
//
//    public static OrderProduct createOrderProduct(Product product) {
//        OrderProduct orderProduct = OrderProduct.builder()
//                .product(product)
//                .orderPrice(product.getPrice())
//                .build();
//        user.removePoint(point);
//
//        return orderProduct;
//    }
//
//
//
//    //주문 취소시 포인트 환불
//    public void cancel() {
//        this.getProduct().addPoint(point);
//    }
//
//
//}
*/
