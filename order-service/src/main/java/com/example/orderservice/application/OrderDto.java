package com.example.orderservice.application;

import com.example.orderservice.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
public class OrderDto implements Serializable {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;


    private String userId;
    private String orderId;

    public OrderDto(String productId, Integer qty, Integer unitPrice, Integer totalPrice, String userId, String orderId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.userId = userId;
        this.orderId = orderId;
    }

    public Order toOrderEntity() {
        return new Order(
                productId,
                qty,
                unitPrice,
                totalPrice,
                userId,
                orderId
        );
    }
}