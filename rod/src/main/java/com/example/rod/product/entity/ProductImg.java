package com.example.rod.product.entity;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "product_img")
public class ProductImg {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_img_id")
    private Long id;

    private String productImgName; //상품이미지 파일명
    private String oriImgName; //원본 이미지 파일명
    private String imgUrl; //이미지 조회 경로
    private String repImgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;


    @Builder
    public ProductImg (Long id, String productImgName, String oriImgName, String imgUrl, String repImgYn, Product product) {
        this.id = id;
        this.productImgName = productImgName;
        this.oriImgName = oriImgName;
        this.imgUrl = imgUrl;
        this.repImgYn = repImgYn;
        this.product = product;
    }

    public void updateProductImg(String oriImgName, String productImgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.productImgName = productImgName;
        this.imgUrl = imgUrl;
    }

}
