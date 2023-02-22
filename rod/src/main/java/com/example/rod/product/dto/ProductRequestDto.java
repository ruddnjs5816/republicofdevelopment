package com.example.rod.product.dto;

import com.example.rod.product.entity.ProductSellStatus;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ProductRequestDto {




    public ProductRequestDto(Long id, String productName, int price, String productImage, String productDescription,
                             ProductSellStatus productSellStatus) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productDescription = productDescription;
        this.productSellStatus = productSellStatus;
    }

    private Long id;
    private String productName;
    private int price;
    private String productImage;
    private String productDescription;

    private ProductSellStatus productSellStatus;


}
