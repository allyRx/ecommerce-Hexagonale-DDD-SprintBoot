package com.vasia.ecommercehexagonale.domain.model;

import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Getter
public class Order {
    private UUID id;
    private String customerId;
    private List<OrderItem> items;
    private BigDecimal totalAmount;
    private OrderStatus status;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private String paymentTransactionId;


    public Order(UUID id, String customerId, List<OrderItem> items, BigDecimal totalAmount, OrderStatus status, LocalDateTime orderDate, String shippingAddress, String paymentTransactionId) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalAmount = totalAmount;
        this.status = status;
        this.orderDate = orderDate;
        this.shippingAddress = shippingAddress;
        this.paymentTransactionId = paymentTransactionId;
    }


    public Order(String customerId, List<CartItem> cartItems,String shippingAddress){
        this.id=UUID.randomUUID();
        this.customerId=customerId;
        this.items= cartItems.stream()
                .map(item -> new OrderItem(item.getProductId(), item.getProductName(),item.getPrice(),item.getQuantity())).collect(Collectors.toList());


        //calcul montant total
        this.totalAmount = items.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        this.status = OrderStatus.PENDING;
        this.orderDate= LocalDateTime.now();
        this.shippingAddress = shippingAddress;
    }

    // Logique métier : Marquer comme payée
    public void markAsPaid(String transactionId) {
        if (this.status != OrderStatus.PENDING) {
            throw new IllegalStateException("Order must be PENDING to be marked as PAID.");
        }
        this.status = OrderStatus.PAID;
        this.paymentTransactionId = transactionId;
    }

    // Logique métier : Marquer comme expédiée
    public void markAsShipped() {
        if (this.status != OrderStatus.PAID) {
            throw new IllegalStateException("Order must be PAID to be marked as SHIPPED.");
        }
        this.status = OrderStatus.SHIPPED;
    }

    // Logique métier : Annuler la commande
    public void cancel() {
        if (this.status == OrderStatus.SHIPPED || this.status == OrderStatus.DELIVERED) {
            throw new IllegalStateException("Cannot cancel a shipped or delivered order.");
        }
        this.status = OrderStatus.CANCELLED;
    }


    public enum OrderStatus {
        PENDING, PAID, SHIPPED, DELIVERED, CANCELLED
    }
}
