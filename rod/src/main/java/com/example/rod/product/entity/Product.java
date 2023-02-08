package com.example.rod.product.entity;

import com.example.rod.product.dto.ProductRequestDto;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
public class Product {

    public Product(Long id, String productName, Long price, String productImage, String productDescription) {
        this.id = id;
        this.productName = productName;
        this.price = price;
        this.productImage = productImage;
        this.productDescription = productDescription;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String productName;
    private Long price;
    private String productImage;
    private String productDescription;


    public Product(ProductRequestDto productRequestDto, Long userId) {
        this.productName = productRequestDto.getProductName();
        this.price = productRequestDto.getPrice();
        this.productDescription = productRequestDto.getProductDescription();
        this.productImage = productRequestDto.getProductImage();

    }

    public Product() {

    }
}
