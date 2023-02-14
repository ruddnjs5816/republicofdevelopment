package com.example.rod.order.controller;

import com.example.rod.order.dto.OrderDto;
import com.example.rod.order.service.OrderService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RequiredArgsConstructor
@Controller
public class OrderController {
    private final OrderService orderService;

    //주문 하기
    @PostMapping("/order")
    public void order(@RequestBody OrderDto orderDto, @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        String username = userDetails.getUsername();
        Long orderId;

        try {
            orderId = orderService.order(orderDto, username);
        } catch (Exception e) {

        }

    }

    //주문 취소 하기
    @PostMapping("/order/{orderId}/cancel")
    public ResponseEntity cancelOrder(@PathVariable("orderId") Long orderId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (!orderService.validateOrder(orderId, userDetails.getUsername())) {
            return new ResponseEntity<String>("주문 취소 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }

        orderService.cancelOrder(orderId);
        return new ResponseEntity<Long>(orderId, HttpStatus.OK);
    }
    //주문 내역 조회

}
