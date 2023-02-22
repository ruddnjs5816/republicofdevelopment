package com.example.rod.product.entity;


import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import com.example.rod.share.TimeStamped;
import lombok.Builder;
import lombok.Getter;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product")
public class Product extends TimeStamped {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId; //상품 번호

    @Column(nullable = false, length = 50)
    private String productName; //상품명
    @Column(name = "price", nullable = false)
    private int price; //상품 가격

    @Column(nullable = false)
    private String productDescription; //상품 설명

    @Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus;  //상품 판매 상태


    @Builder
    public Product(ProductRequestDto productRequestDto) {
        this.productName = productRequestDto.getProductName();
        this.price = productRequestDto.getPrice();
        this.productDescription = productRequestDto.getProductDescription();
        this.productSellStatus = productRequestDto.getProductSellStatus();

    }


    public void changeProductStatus(ProductModifyRequestDto productModifyRequestDto) {
        this.productName = productModifyRequestDto.getProductName();
        this.price = productModifyRequestDto.getPrice();
        this.productDescription = productModifyRequestDto.getProductDescription();
        this.productSellStatus = productModifyRequestDto.getProductSellStatus();

    }



}
