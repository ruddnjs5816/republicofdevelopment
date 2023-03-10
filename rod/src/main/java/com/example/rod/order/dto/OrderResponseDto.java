package com.example.rod.order.dto;

import com.example.rod.order.entity.Order;
import com.example.rod.order.entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Long orderId;
//    private final Long productId;
    private  String  createdAt;
//    private final OrderStatus orderStatus;

    private String productName; //상품명

    private Integer price; //상품 가격

    private String productDescription; //상품 설명

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


//    public OrderResponseDto(Order order) {
//        this.orderId = order.getOrderId();
//        this.productId = order.getProductId();
//        this.orderDate = order.getOrderDate();
//    }

    public OrderResponseDto(Order order) {
        this.orderId = order.getId();
        this.productName = order.getProductName();
        this.price = order.getPrice();
        this.productDescription = order.getProductDescription();
        this.orderStatus = order.getOrderStatus();
        this.createdAt = createdAt(order.getCreatedAt());
    }

    public String createdAt(LocalDateTime createdAt){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        String formattedTime = createdAt.format(formatter);
        return formattedTime;
    }
}
