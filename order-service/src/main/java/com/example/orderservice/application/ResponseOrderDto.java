package com.example.orderservice.application;

import com.example.orderservice.domain.Order;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ResponseOrderDto {

    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private LocalDateTime createdAt;

    private String orderId;

    public static ResponseOrderDto of(Order order){
        return new ResponseOrderDto(
                order.getProductId(),
                order.getQty(),
                order.getUnitPrice(),
                order.getTotalPrice(),
                order.getCreatedAt(),
                order.getOrderId()
        );
    }

    public ResponseOrderDto(String productId, Integer qty, Integer unitPrice, Integer totalPrice, LocalDateTime createdAt, String orderId) {
        this.productId = productId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.createdAt = createdAt;
        this.orderId = orderId;
    }
}
