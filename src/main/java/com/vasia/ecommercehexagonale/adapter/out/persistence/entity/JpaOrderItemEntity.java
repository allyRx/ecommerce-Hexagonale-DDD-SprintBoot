package com.vasia.ecommercehexagonale.adapter.out.entity;

import com.vasia.ecommercehexagonale.domain.model.OrderItem;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "order_items")
@Data
@NoArgsConstructor
public class JpaOrderItemEntity {
    @Id
    private UUID id = UUID.randomUUID();
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public JpaOrderItemEntity(UUID productId, String productName, BigDecimal price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    public static OrderItem toDomain(JpaOrderItemEntity entity) {
        return new OrderItem(
                entity.getProductId(),
                entity.getProductName(),
                entity.getPrice(),
                entity.getQuantity()
        );
    }

    public static JpaOrderItemEntity fromDomain(OrderItem domain) {
        return new JpaOrderItemEntity(
                domain.getProductId(),
                domain.getProductName(),
                domain.getPrice(),
                domain.getQuantity()
        );
    }
}