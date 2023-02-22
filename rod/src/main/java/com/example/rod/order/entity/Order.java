package com.example.rod.order.entity;

import com.example.rod.share.TimeStamped;
import com.example.rod.user.entity.User;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "orders")
public class Order extends TimeStamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;


//    private Long userId; //주문 회원

    private Long productId; //주문 상품

    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private LocalDateTime orderDate; //주문 날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus; //주문 상태 [ORDER, CANCEL]


    public Order(Long userId, Long productId) {
//        this.userId = userId;
        this.productId = productId;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
    }




    //주문 취소 시 주문 상태 취소로 변환
    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCEL;
    }

}
