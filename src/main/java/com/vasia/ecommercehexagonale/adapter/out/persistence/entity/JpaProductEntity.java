package com.vasia.ecommercehexagonale.adapter.out.persistence.entity;

import com.vasia.ecommercehexagonale.domain.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class JpaProductEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int stockQuantity;
    private String vendorId;

    public static JpaProductEntity fromDomain(Product domain) {
        return new JpaProductEntity(
                domain.getId(),
                domain.getName(),
                domain.getDescription(),
                domain.getPrice(),
                domain.getStockQuantity(),
                domain.getVendorId()
        );
    }
    public  static Product toDomain(JpaProductEntity entity){
        return new Product(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice(),
                entity.getStockQuantity(),
                entity.getVendorId()
        );
    }

}
