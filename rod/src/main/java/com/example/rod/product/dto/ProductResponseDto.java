package com.example.rod.product.dto;

import com.example.rod.product.entity.Product;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductResponseDto {
    private Long productId;
    private String productName;
    private Long price;
    private String productImage;
    private String productDescription;


    public ProductResponseDto(Product product) {
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.productImage = product.getProductImage();
        this.productDescription = product.getProductDescription();
    }
}
