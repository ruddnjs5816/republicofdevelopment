package com.example.rod.product.dto;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ProductImgDto {
    private Long id;
    private String productImgName; //상품이미지 파일명
    private String oriImgName; //원본 이미지 파일명
    private String imgUrl; //이미지 조회 경로
    private String repImgYn; //대표 이미지 여부

    @Builder
    public ProductImgDto (Long id, String productImgName, String oriImgName, String imgUrl, String repImgYn) {
        this.id = id;
        this.productImgName = productImgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
    }
}
