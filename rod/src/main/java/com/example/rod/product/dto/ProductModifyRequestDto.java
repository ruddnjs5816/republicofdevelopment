package com.example.rod.product.dto;

import com.example.rod.product.entity.ProductSellStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ProductModifyRequestDto {
    private Long id;
    private String productName;
    private int price;
    private String productImage;
    private String productDescription;
    private int stockQuantity;
    private ProductSellStatus productSellStatus;
    private List<ProductImgDto> productImgDtoList = new ArrayList<>(); //상품 저장 후 수정 시 상품이미지 정보 저장

    private List<Long> productImgIds = new ArrayList<>(); //수정 시 상품이미지 아이디 담아둘 용도

}
