package com.denton.shop.clothshop.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "ordered_on")
    private LocalDateTime orderedOn;
    @Column(columnDefinition = "text")
    private String orderAddress;
    @Column(name = "payment_id")
    private String paymentId;

    private Long[] items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "order_total_price")
    private BigDecimal orderTotalPrice;
}
