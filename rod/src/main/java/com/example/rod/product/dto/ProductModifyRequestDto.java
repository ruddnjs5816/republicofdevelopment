package com.example.rod.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductModifyRequestDto {
    private String productName;
    private Long price;
    private String productImage;
    private String productDescription;
}
