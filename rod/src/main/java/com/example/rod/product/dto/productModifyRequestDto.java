package com.example.rod.product.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class productModifyRequestDto {
    private String itemName;
    private Long point;
    private String itemImage;
    private String itemDescription;
}
