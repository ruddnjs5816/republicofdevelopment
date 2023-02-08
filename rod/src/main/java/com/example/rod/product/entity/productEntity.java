package com.example.rod.product.entity;

import com.example.rod.product.dto.productModifyRequestDto;
import lombok.Getter;

import com.example.rod.product.dto.productRequestDto;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class productEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String itemName;
    private Long point;
    private String itemImage;
    private String itemDescription;


    public productEntity(productRequestDto productRequestDto) {
        this.itemName = productRequestDto.getItemName();
        this.point = productRequestDto.getPoint();
        this.itemImage = productRequestDto.getItemImage();
        this.itemDescription = productRequestDto.getItemDescription();

    }


    public void changeProductStatus(productModifyRequestDto productModifyRequestDto) {
        this.itemName = productModifyRequestDto.getItemName();
        this.point = productModifyRequestDto.getPoint();
        this.itemImage = productModifyRequestDto.getItemImage();
        this.itemDescription = productModifyRequestDto.getItemDescription();
    }
}
