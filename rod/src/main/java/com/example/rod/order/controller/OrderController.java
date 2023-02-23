package com.example.rod.order.controller;

import com.example.rod.order.dto.OrderDto;
import com.example.rod.order.dto.OrderResponseDto;
import com.example.rod.order.service.OrderService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //주문 하기
    @PostMapping("/shop/orders")
    public ResponseEntity<String> order(@RequestBody OrderDto orderDto,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.order(orderDto, userDetails.getUser().getUserId());
        return new ResponseEntity<>("주문 완료", HttpStatus.CREATED);
    }
    //주문 취소 하기
    @PostMapping("/shop/orders/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails )
        {
            orderService.cancelOrder(orderId, userDetails.getUser().getUserId());
            return new ResponseEntity<>("오더 취소완료", HttpStatus.OK);
        }


    //주문 내역 조회 -유저 마이페이지 내에서 확인 가능
    @GetMapping("shop/orders/")
    public List<OrderResponseDto> getMyOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getMyOrders(userDetails.getUser().getUserId());
    }
}
