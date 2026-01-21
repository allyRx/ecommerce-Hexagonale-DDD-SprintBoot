package com.vasia.ecommercehexagonale.adapter.in.web;

import com.vasia.ecommercehexagonale.adapter.in.dto.OrderRequest;
import com.vasia.ecommercehexagonale.adapter.in.dto.OrderResponse;
import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.model.Order;
import com.vasia.ecommercehexagonale.domain.port.in.CartManagementPort;
import com.vasia.ecommercehexagonale.domain.port.in.OrderManagementPort;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@AllArgsConstructor
public class OrderController {

    private final OrderManagementPort orderManagementPort;
    private final CartManagementPort cartManagementPort;


    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {
        Cart cart = cartManagementPort.getOrCreateCart(request.getCustomerId());
        try {
            Order order = orderManagementPort.placeOrder(request.getCustomerId(), cart, request.getShippingAddress());
            cartManagementPort.clearCart(request.getCustomerId());
            return new ResponseEntity<>(toResponse(order), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable UUID orderId) {
        try {
            Order order = orderManagementPort.cancelOrder(orderId);
            return new ResponseEntity<>(toResponse(order), HttpStatus.OK);
        } catch (IllegalArgumentException | IllegalStateException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable UUID orderId) {
        return orderManagementPort.getOrderById(orderId)
                .map(this::toResponse)
                .map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByCustomerId(@PathVariable String customerId) {
        List<OrderResponse> orders = orderManagementPort.getOrdersByCustomerId(customerId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    private OrderResponse toResponse(Order order) {
        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
                .map(item -> new OrderResponse.OrderItemResponse(
                        item.getProductId().toString(),
                        item.getProductName(),
                        item.getPrice(),
                        item.getQuantity(),
                        item.getTotalPrice()
                ))
                .collect(Collectors.toList());
        return new OrderResponse(
                order.getId().toString(),
                order.getCustomerId(),
                itemResponses,
                order.getTotalAmount(),
                order.getStatus().name(),
                order.getOrderDate(),
                order.getShippingAddress(),
                order.getPaymentTransactionId()
        );
    }
}
