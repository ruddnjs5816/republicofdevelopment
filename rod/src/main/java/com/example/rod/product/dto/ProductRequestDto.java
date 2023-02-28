package com.example.rod.product.dto;

import com.example.rod.product.entity.ProductSellStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class ProductRequestDto {

    private Long id;
    private String productName;
    private Integer price;
    private String productImage;
    private String productDescription;
    private ProductSellStatus productSellStatus;

}
