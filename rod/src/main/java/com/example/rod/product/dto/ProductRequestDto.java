package com.example.rod.product.dto;

import com.example.rod.product.entity.ProductSellStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    private String productName;
    private Integer price;
    //    private String productImage;
    private String productDescription;



}
