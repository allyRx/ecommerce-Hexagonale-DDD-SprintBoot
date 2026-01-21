package com.vasia.ecommercehexagonale.adapter.in.dto;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private String id;
    private String customerId;
    private List<CartItemResponse> items;
    private BigDecimal totalCartPrice;

    public CartResponse(String id, String customerId, List<CartItemResponse> items, BigDecimal totalCartPrice) {
        this.id = id;
        this.customerId = customerId;
        this.items = items;
        this.totalCartPrice = totalCartPrice;
    }

    public String getId() { return id; }
    public String getCustomerId() { return customerId; }
    public List<CartItemResponse> getItems() { return items; }
    public BigDecimal getTotalCartPrice() { return totalCartPrice; }

    public static class CartItemResponse {
        private String productId;
        private String productName;
        private BigDecimal price;
        private int quantity;
        private BigDecimal totalPrice;

        public CartItemResponse(String productId, String productName, BigDecimal price, int quantity, BigDecimal totalPrice) {
            this.productId = productId;
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.totalPrice = totalPrice;
        }

        public String getProductId() { return productId; }
        public String getProductName() { return productName; }
        public BigDecimal getPrice() { return price; }
        public int getQuantity() { return quantity; }
        public BigDecimal getTotalPrice() { return totalPrice; }
    }
}
