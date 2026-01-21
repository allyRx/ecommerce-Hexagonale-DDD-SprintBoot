package com.vasia.ecommercehexagonale.adapter.in.dto;

public class OrderRequest {
    private String customerId;
    private String shippingAddress;

    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
}
