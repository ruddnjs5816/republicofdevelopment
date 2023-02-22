package com.example.rod.order.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@NoArgsConstructor
public class OrderDto {
    private Long productId;

    @Builder
    public OrderDto(Long productId) {
        this.productId = productId;
    }
}
