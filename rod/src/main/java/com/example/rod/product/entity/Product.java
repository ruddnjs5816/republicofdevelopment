package com.example.rod.product.entity;

import com.example.rod.product.dto.ProductRequestDto;
import com.example.rod.product.dto.ProductModifyRequestDto;
import lombok.Getter;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;
    private String productName; //상품 이름
    private Long price; //상품 가격
    private String productImage; //상품 이미지
    private String productDescription; //상품 설명

    private int count; //상품 판매 개수
    private int stock; //상품 재고

    private boolean isSoldOut; //상품 상태(판매중/품절)

    @OneToMany(mappedBy = "product")
    private List<WishProduct> wish_products = new ArrayList<>();


    public Product(ProductRequestDto productRequestDto) {
        this.productName = productRequestDto.getProductName();
        this.price = productRequestDto.getPrice();
        this.productImage = productRequestDto.getProductImage();
        this.productDescription = productRequestDto.getProductDescription();

    }


    public void changeProductStatus(ProductModifyRequestDto productModifyRequestDto) {
        this.productName = productModifyRequestDto.getProductName();
        this.price = productModifyRequestDto.getPrice();
        this.productImage = productModifyRequestDto.getProductImage();
        this.productDescription = productModifyRequestDto.getProductDescription();
    }
}
