package com.vasia.ecommercehexagonale.adapter.out.persistence.entity;


import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.model.CartItem;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Entity
@Table(name = "carts")
@Data
@NoArgsConstructor
public class JpaCartEntity {
    @Id
    private UUID id;
    private String customerId;

    @ElementCollection
    @CollectionTable(name = "cart_items", joinColumns = @JoinColumn(name = "cart_id"))
    @MapKeyColumn(name = "product_id")
    private Map<UUID, JpaCartItemEmbeddable> items;
    private String appliedDiscountCode;
    private java.math.BigDecimal discountPercentage;

    public JpaCartEntity(UUID id, String customerId, Map<UUID, JpaCartItemEmbeddable> items, String appliedDiscountCode, java.math.BigDecimal discountPercentage) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.appliedDiscountCode = appliedDiscountCode;
        this.discountPercentage = discountPercentage;
    }

    public static Cart toDomain(JpaCartEntity entity) {
        Map<UUID, CartItem> domainItems = entity.getItems().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> JpaCartItemEmbeddable.toDomain(entry.getValue())
                ));
        Cart cart = new Cart(entity.getId(), entity.getCustomerId(), domainItems);
        if (entity.getAppliedDiscountCode() != null) {
            cart.applyDiscount(entity.getAppliedDiscountCode(), entity.getDiscountPercentage());
        }
        return cart;
    }

    public static JpaCartEntity fromDomain(Cart domain) {
        Map<UUID, JpaCartItemEmbeddable> entityItems = domain.getItems().entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> JpaCartItemEmbeddable.fromDomain(entry.getValue())
                ));
        return new JpaCartEntity(domain.getId(), domain.getCustomerId(), entityItems, domain.getAppliedDiscountCode(), domain.getDiscountPercentage());
    }
}
