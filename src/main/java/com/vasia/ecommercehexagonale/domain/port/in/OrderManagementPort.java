package com.vasia.ecommercehexagonale.domain.port.in;

import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.model.Order;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderManagementPort {
    Order placeOrder(String customerId, Cart cart, String shippingAddress);
    Optional<Order> getOrderById(UUID orderId);
    List<Order> getOrdersByCustomerId(String customerId);
    Order cancelOrder(UUID orderId);
}

