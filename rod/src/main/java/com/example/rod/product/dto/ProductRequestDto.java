package com.example.rod.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequestDto {

    private final String productName;
    private final Long price;
    private final String productImage;
    private final String productDescription;

}
