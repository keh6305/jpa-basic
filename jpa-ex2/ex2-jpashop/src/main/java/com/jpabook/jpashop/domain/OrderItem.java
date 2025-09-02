package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.item.Item;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    private int orderPrice;     // 주문 가격
    private int count;          // 주문 수량

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "orders_id")
    private Orders orders;
}