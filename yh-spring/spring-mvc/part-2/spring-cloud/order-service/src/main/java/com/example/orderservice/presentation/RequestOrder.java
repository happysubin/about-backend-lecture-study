package com.example.orderservice.presentation;

import com.example.orderservice.application.OrderDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class RequestOrder {
    private String productId;
    private Integer qty;
    private Integer unitPrice;

    public OrderDto toServiceDto(String userId) {
        return new OrderDto(
                productId,
                qty,
                unitPrice,
                qty * unitPrice,
                userId,
                UUID.randomUUID().toString()
        );
    }
}
