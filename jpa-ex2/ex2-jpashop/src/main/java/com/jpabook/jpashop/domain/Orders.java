package com.jpabook.jpashop.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Orders {
    @Id
    @GeneratedValue
    @Column(name = "orders_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    private LocalDateTime OrderDate;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}