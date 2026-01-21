package com.vasia.ecommercehexagonale.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class OrderItem {
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private int quantity;


    public OrderItem(UUID productId , String productName, BigDecimal price, int quantity){
        this.productId=productId;
        this.productName=productName;
        this.price=price;
        this.quantity=quantity;
    }


    public BigDecimal getTotalPrice(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
