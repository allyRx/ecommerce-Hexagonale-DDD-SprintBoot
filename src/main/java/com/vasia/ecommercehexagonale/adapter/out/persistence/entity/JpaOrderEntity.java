package com.vasia.ecommercehexagonale.adapter.out.entity;

import com.vasia.ecommercehexagonale.domain.model.Order;
import com.vasia.ecommercehexagonale.domain.model.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor @NoArgsConstructor
public class JpaOrderEntity {
    @Id
    private UUID id;
    private String customerId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "order_id")
    private List<JpaOrderItemEntity> items;

    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    private Order.OrderStatus status;

    private LocalDateTime orderDate;
    private String shippingAddress;
    private String paymentTransactionId;


    public static Order toDomain(JpaOrderEntity entity) {
        List<OrderItem> domainItems = entity.getItems().stream()
                .map(JpaOrderItemEntity::toDomain)
                .collect(Collectors.toList());
        return new Order(
                entity.getId(),
                entity.getCustomerId(),
                domainItems,
                entity.getTotalAmount(),
                entity.getStatus(),
                entity.getOrderDate(),
                entity.getShippingAddress(),
                entity.getPaymentTransactionId()
        );
    }

    public static JpaOrderEntity fromDomain(Order domain) {
        List<JpaOrderItemEntity> entityItems = domain.getItems().stream()
                .map(JpaOrderItemEntity::fromDomain)
                .collect(Collectors.toList());
        return new JpaOrderEntity(
                domain.getId(),
                domain.getCustomerId(),
                entityItems,
                domain.getTotalAmount(),
                domain.getStatus(),
                domain.getOrderDate(),
                domain.getShippingAddress(),
                domain.getPaymentTransactionId()
        );
    }
}
