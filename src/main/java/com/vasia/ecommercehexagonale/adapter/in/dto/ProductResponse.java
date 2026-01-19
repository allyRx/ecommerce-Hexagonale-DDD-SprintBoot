package com.vasia.ecommercehexagonale.adapter.in.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;


@Data @RequiredArgsConstructor
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String vendorId;

    public ProductResponse(String id, String name, String description, BigDecimal price, int stockQuantity, String vendorId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.vendorId = vendorId;
    }

}
