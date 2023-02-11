package com.example.rod.product.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequestDto {

    public ProductRequestDto(String productName, Long price, String productImage, String productDescription) {
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    private String productName;
    private Long price;
    private String productImage;
    private String productDescription;


}
