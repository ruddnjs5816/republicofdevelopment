package com.example.rod.product.entity;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import lombok.Getter;

import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName;
    private Long price;
    private String productImage;
    private String productDescription;


    public Product(ProductRequestDto productRequestDto) {
        this.productName = productRequestDto.getProductName();
        this.price = productRequestDto.getPrice();
        this.productImage = productRequestDto.getProductImage();
        this.productDescription = productRequestDto.getProductDescription();

    }


    public void changeProductStatus(ProductModifyRequestDto productModifyRequestDto) {
        this.productName = productModifyRequestDto.getProductName();
        this.price = productModifyRequestDto.getPoint();
        this.productImage = productModifyRequestDto.getProductImage();
        this.productDescription = productModifyRequestDto.getProductDescription();
    }
}
