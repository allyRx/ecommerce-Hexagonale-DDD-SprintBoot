package com.vasia.ecommercehexagonale.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;


@Getter
public class Product {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String vendorId;

    // Constructeur complet (utilisé par l'adaptateur de persistance)
    public Product(UUID id, String name, String description, BigDecimal price, int stockQuantity, String vendorId){
        this.id=id;
        this.name=name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.vendorId = vendorId;
    }

    // Constructeur pour la création d'un nouveau produit
    public Product(String name, String description, BigDecimal price, int stockQuantity, String vendorId) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
        this.vendorId = vendorId;
    }

    //Logique Metier

    //decrementer le stock
    public void decreaseStock(int quantity){
        if(this.stockQuantity < quantity){
            throw new IllegalArgumentException("Stock not enough for this prodcut, "+ name);
        }

        this.stockQuantity -= quantity;
    }

    public void increaseStock(int quantity){
        this.stockQuantity += quantity;
    }

    public boolean isAvailable(int quantity){
        return this.stockQuantity >= quantity;
    }
}
