package com.vasia.ecommercehexagonale.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

// Contient toute la logique de gestion du panier.
public class Cart { @Getter
private UUID id;

    @Getter
    private String customerId;

    // Le panier stocke les articles sous forme de Map<ProductId, CartItem>
    private Map<UUID, CartItem> items;
    @Getter
    private String appliedDiscountCode;
    @Getter
    private BigDecimal discountPercentage = BigDecimal.ZERO;


    // Constructeur complet (pour la reconstruction depuis la persistance)
    public Cart(UUID id, String customerId, Map<UUID, CartItem> items) {
        this.id = id;
        this.customerId = customerId;
        this.items = new HashMap<>(items);
    }


    // Constructeur pour un nouveau panier
    public Cart(String customerId) {
        this.id = UUID.randomUUID();
        this.customerId = customerId;
        this.items = new HashMap<>();
    }


    // Retourne une vue non modifiable des articles
    public Map<UUID, CartItem> getItems() { return
            Collections.unmodifiableMap(items); }


    // Logique métier : Ajouter un article
    public void addItem(Product product, int quantity) {
        if (items.containsKey(product.getId())) {
        // Si l'article existe, on met à jour la quantité
            CartItem existingItem = items.get(product.getId());
            existingItem.updateQuantity(existingItem.getQuantity() + quantity);

        } else {// Sinon, on ajoute un nouvel article
            items.put(product.getId(), new CartItem(product.getId(),
                    product.getName(), product.getPrice(), quantity));
        }

    }


    // Logique métier : Retirer un article
    public void removeItem(UUID productId) {
        items.remove(productId);
    }


    // Logique métier : Mettre à jour la quantité d'un article
    public void updateItemQuantity(UUID productId, int quantity) {
        if (items.containsKey(productId)) {
            items.get(productId).updateQuantity(quantity);
        }
    }


    // Logique métier : Calcul du prix total du panier (avec réduction)
    public BigDecimal getTotalCartPrice() {
        BigDecimal subtotal = items.values().stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        if (discountPercentage.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal discount =
                    subtotal.multiply(discountPercentage).divide(new BigDecimal(100));
            return subtotal.subtract(discount);
        }
        return subtotal;
    }


    // Logique métier : Appliquer une réduction
    public void applyDiscount(String code, BigDecimal percentage) {
        this.appliedDiscountCode = code;
        this.discountPercentage = percentage;
    }


    // Logique métier : Vider le panier
    public void clear() {
        items.clear();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }


}