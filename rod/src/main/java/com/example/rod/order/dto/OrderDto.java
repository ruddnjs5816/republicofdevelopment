package com.example.rod.order.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderDto {
    private Long productId;

    @Builder
    public OrderDto(Long productId) {
        this.productId = productId;
    }
}
