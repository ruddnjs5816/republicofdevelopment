package com.example.rod.product.entity;

import com.example.rod.exception.OutOfStockException;
import com.example.rod.order.entity.BaseEntity;
import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
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
public class Product extends BaseEntity {


    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long Id; //상품 번호

    @Column(nullable = false, length = 50)
    private String productName; //상품명
    @Column(name = "price", nullable = false)
    private int price; //상품 가격

    @Column(nullable = false)
    private String productDescription; //상품 설명

    @Column(nullable = false)
    private int stockQuantity; //상품 재고 수량

    @Enumerated(EnumType.STRING)
    private ProductSellStatus productSellStatus;  //상품 판매 상태


    @Builder
    public Product(ProductRequestDto productRequestDto) {
        this.productName = productRequestDto.getProductName();
        this.price = productRequestDto.getPrice();
        this.productDescription = productRequestDto.getProductDescription();
        this.stockQuantity = productRequestDto.getStockQuantity();
        this.productSellStatus = productRequestDto.getProductSellStatus();

    }


    public void changeProductStatus(ProductModifyRequestDto productModifyRequestDto) {
        this.productName = productModifyRequestDto.getProductName();
        this.price = productModifyRequestDto.getPrice();
        this.productDescription = productModifyRequestDto.getProductDescription();
        this.stockQuantity = productModifyRequestDto.getStockQuantity();
        this.productSellStatus = productModifyRequestDto.getProductSellStatus();

    }

    //재고 관련 메서드
    public void addStock(int stockQuantity) {
        this.stockQuantity += stockQuantity;
    }

    public void removeStock(int stockQuantity) {
        int restStock = this.stockQuantity - stockQuantity;
        if (restStock < 0) {
            throw new OutOfStockException("상품의 재고가 부족합니다.(현재 재고 수량: " + this.stockQuantity + ")");
        }
        this.stockQuantity = restStock;
    }

}
