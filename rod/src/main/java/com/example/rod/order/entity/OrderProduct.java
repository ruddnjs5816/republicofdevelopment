package com.example.rod.order.entity;

import com.example.rod.product.entity.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct {

    @Id @GeneratedValue
    @Column(name = "order_product_id")
    private Long Id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product; //주문 상품

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order; //주문
    private int orderPrice; //주문 가격
    private int count; //주문 수량


    public static OrderProduct createOrderProduct(Product product, int orderPrice, int count) {
        OrderProduct orderProduct = new OrderProduct();
        orderProduct.setProduct(product);
        orderProduct.setOrderPrice(orderPrice);
        orderProduct.setCount(count);

        product.removeStock(count);
        return orderProduct;
    }


    //주문 취소
    public void cancel() {
        getProduct().addStock(count);
    }


}
