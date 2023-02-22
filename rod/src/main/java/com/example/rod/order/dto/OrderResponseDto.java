package com.example.rod.order.dto;

import com.example.rod.order.entity.Order;
import com.example.rod.order.entity.OrderStatus;
import com.example.rod.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class OrderResponseDto {

    private final Long orderId;
    private final Long productId;
    private final LocalDateTime orderDate;
    private final OrderStatus orderStatus;

    public OrderResponseDto(Order order) {
        this.orderId = order.getOrderId();
        this.productId = order.getProductId();
        this.orderDate = order.getOrderDate();
        this.orderStatus = order.getOrderStatus();
    }
}
