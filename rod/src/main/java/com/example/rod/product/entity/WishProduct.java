package com.example.rod.product.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Getter
@Entity
public class WishProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="wish_id")
    private Wish wish;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="product_id")
    private Product product;

    private int wishCount; // 위시 등록 상품의 수량

    //이미 담겨있는 물건 또 담을 경우 수량증가
    public void addCount(int wishCount) {
        this.wishCount += wishCount;
    }
}
