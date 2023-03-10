package com.example.rod.order.entity;

import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "orders")
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String productName; //상품명

    private Integer price; //상품 가격

    private String productDescription; //상품 설명


    private Long productId; //주문 상품

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태 [ORDER, CANCEL]


    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime orderDate; //주문 날짜


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Order(User user, Long productId, Integer price, String productDescription, String productName, OrderStatus order) {
        this.user = user;
        this.productId = productId;
        this.price = price;
        this.productDescription = productDescription;
        this.productName = productName;
        this.orderStatus = order;
    }




    //    주문 성공 시 주문 상태 주문으로 변환
    public void successOrder() {
        this.orderStatus = OrderStatus.ORDER;
    }




//    주문 취소 시 주문 상태 취소로 변환
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
    }

}
