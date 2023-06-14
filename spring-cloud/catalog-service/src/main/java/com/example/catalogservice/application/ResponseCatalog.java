package com.example.catalogservice.application;

import com.example.catalogservice.domain.Catalog;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@NoArgsConstructor
public class ResponseCatalog {

    private String productId;
    private String productName;
    private Integer unitPrice;
    private Integer stock;
    private LocalDateTime createdAt;

    public static ResponseCatalog of(Catalog catalog){
        return new ResponseCatalog(
                catalog.getProductId(),
                catalog.getProductName(),
                catalog.getUnitPrice(),
                catalog.getStock(),
                catalog.getCreatedAt()
        );
    }


    public ResponseCatalog(String productId, String productName, Integer unitPrice, Integer stock, LocalDateTime createdAt) {
        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.stock = stock;
        this.createdAt = createdAt;
    }
}
