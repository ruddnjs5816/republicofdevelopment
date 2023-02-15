package com.example.rod.order.entity;

import com.example.rod.product.entity.Product;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.bytebuddy.implementation.bind.annotation.BindingPriority;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@Table(name = "order_product")
public class OrderProduct extends BaseEntity {

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


    @Builder
    public OrderProduct(Product product, Order order, int orderPrice, int count) {
        this.product = product;
        this.order = order;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public void setOrder(Order order) {this.order = order;}



    public static OrderProduct createOrderProduct(Product product, int count) {
        OrderProduct orderProduct = OrderProduct.builder()
                .product(product)
                .count(count)
                .orderPrice(product.getPrice())
                .build();
        product.removeStock(count);

        return orderProduct;
    }

    public int getTotalPrice() {
        return orderPrice * count;
    }

    //주문 취소시 재고 수량 증가
    public void cancel() {
        this.getProduct().addStock(count);
    }


}
