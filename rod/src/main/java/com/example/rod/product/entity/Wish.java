package com.example.rod.product.entity;

import com.example.rod.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Wish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    User user; // 해당 유저의 장바구니 -> 유저 당 하나의 장바구니 소유

    private int count; //위시에 담긴 총 상품 개수

    @OneToMany(mappedBy = "wish")
    private List<WishProduct> wish_products = new ArrayList<>();
}
