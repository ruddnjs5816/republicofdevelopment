package com.example.rod.order.controller;

import com.example.rod.order.dto.OrderResponseDto;
import com.example.rod.order.service.OrderService;
import com.example.rod.security.details.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    //주문 하기
    @PostMapping("/shop/orders/{productId}")
    public ResponseEntity<String> order(@PathVariable Long productId,
                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        orderService.order(productId,  userDetails);
        return new ResponseEntity<>("주문 완료", HttpStatus.CREATED);
    }


    //주문 취소 하기
    @PostMapping("/shop/orders/cancel/{orderId}")
    public ResponseEntity<String> cancelOrder(@PathVariable Long orderId,
                                              @AuthenticationPrincipal UserDetailsImpl userDetails ) {
            orderService.cancelOrder(orderId, userDetails.getUser());
            return new ResponseEntity<>("오더 취소완료", HttpStatus.OK);
        }


    //주문 내역 조회 -유저 마이페이지 내에서 확인 가능
//    @GetMapping("shop/orderList")
//    public List<OrderResponseDto> getMyOrders
//    (@RequestParam(defaultValue = "1") int page,
//     @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable,
//     @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return orderService.getMyOrders(pageable, page, userDetails);
//    }


    //주문 내역 조회 -유저 마이페이지 내에서 확인 가능
//    @GetMapping("shop/orderList")
//    public List<OrderResponseDto> getMyOrders
//    (@AuthenticationPrincipal UserDetailsImpl userDetails) {
//        return orderService.getMyOrders(userDetails);
//    }


    @GetMapping("shop/orderList")
    public List<OrderResponseDto> getMyOrders
            (@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getMyOrders(userDetails.getUser());
    }
}
