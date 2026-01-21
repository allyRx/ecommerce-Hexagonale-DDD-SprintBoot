package com.vasia.ecommercehexagonale.adapter.out.persistence.entity;

import com.vasia.ecommercehexagonale.domain.model.CartItem;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Embeddable
@Data
@NoArgsConstructor
public class JpaCartItemEmbeddable {
    @Transient
    private UUID productId;
    private String productName;
    private BigDecimal price;
    private int quantity;

    public JpaCartItemEmbeddable(UUID productId, String productName, BigDecimal price, int quantity) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
    }

    // Mapping : Embeddable -> Modèle de Domaine
    public static CartItem toDomain(UUID productId, JpaCartItemEmbeddable embeddable) {
        return new CartItem(productId, embeddable.productName,
                embeddable.price, embeddable.quantity);
    }
    // Mapping : Modèle de Domaine -> Embeddable
    public static JpaCartItemEmbeddable fromDomain(CartItem domain) {
        return new JpaCartItemEmbeddable(domain.getProductId(),
                domain.getProductName(), domain.getPrice(), domain.getQuantity());
    }
}
