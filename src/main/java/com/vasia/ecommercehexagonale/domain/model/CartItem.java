package com.vasia.ecommercehexagonale.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

public class CartItem {
    @Getter
    private UUID productId;
    @Getter
    private String productName;
    @Getter
    private final BigDecimal price;
    @Getter
    private int quantity;

    public CartItem(UUID productId, String productName,BigDecimal price, int quantity){
        this.productId=productId;
        this.productName=productName;
        this.price=price;
        this.quantity=quantity;
    }

    // Logique métier : Calcul du prix total pour cet article
    public BigDecimal getTotalPrice(){
        return price.multiply(BigDecimal.valueOf(quantity));
    }

    // Logique métier : Mise à jour de la quantité
    public void updateQuantity(int newQuantity){
        if(newQuantity <= 0){
           throw new IllegalArgumentException("QUantity must be positive");
        }

        this.quantity= newQuantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;CartItem cartItem = (CartItem) o;
        return Objects.equals(productId, cartItem.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}
