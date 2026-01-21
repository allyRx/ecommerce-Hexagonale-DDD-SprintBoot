package com.vasia.ecommercehexagonale.domain.service;

import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.model.Order;
import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.in.OrderManagementPort;
import com.vasia.ecommercehexagonale.domain.port.out.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class OrderService implements OrderManagementPort {

    private final OrderRepositoryPort orderRepositoryPort;
    private final ProductRepositoryPort productRepositoryPort;

    private final PaymentGatewayPort paymentGatewayPort;
    private final PaymentCustomerPort paymentCustomerPort;
    private final ShippingServicePort shippingServicePort;
    private final NotificationPort notificationPort;

    public OrderService(OrderRepositoryPort orderRepositoryPort, ProductRepositoryPort productRepositoryPort, PaymentGatewayPort paymentGatewayPort, PaymentCustomerPort paymentCustomerPort, ShippingServicePort shippingServicePort, NotificationPort notificationPort){
        this.productRepositoryPort = productRepositoryPort;
        this.orderRepositoryPort = orderRepositoryPort;
        this.paymentGatewayPort = paymentGatewayPort;
        this.paymentCustomerPort = paymentCustomerPort;
        this.shippingServicePort = shippingServicePort;
        this.notificationPort = notificationPort;
    }

    @Override
    public Order placeOrder(String customerId, Cart cart, String shippingAddress) {

        if(cart.isEmpty()){
            throw new IllegalArgumentException("Cannot place order with an empty cart");
        }

        //verify product and get all infos for each item
        cart.getItems().forEach((productId, cartItem)->{
            Product product = productRepositoryPort.fidById(productId).orElseThrow(()-> new IllegalArgumentException("Product Empty"));
            product.decreaseStock(cartItem.getQuantity());
            productRepositoryPort.save(product);
        });

        //create order
        Order order = new Order(customerId,cart.getItems().values().stream().toList(),shippingAddress);
        Order saveOrder = orderRepositoryPort.save(order);

        //ensure customer exists in payment system
        String stripeCustomerId = paymentCustomerPort.createOrRetrieveCustomer(customerId, customerId + "@customer.local");

        //process payment with the valid Stripe customer ID
        String transactionId = paymentGatewayPort.processPayment(saveOrder.getTotalAmount(), stripeCustomerId);
        saveOrder.markAsPaid(transactionId);
        orderRepositoryPort.save(saveOrder);

        //shipping
        BigDecimal shippingCost = shippingServicePort.calculateShippingCost(shippingAddress, saveOrder.getTotalAmount());
        saveOrder.markAsShipped();
        orderRepositoryPort.save(saveOrder);


        //notification
        notificationPort.sendNotification(customerId, "Your order" + saveOrder.getId()+ "has been placed and shipped!!");

        return  saveOrder;

    }

    @Override
    public Optional<Order> getOrderById(UUID orderId) {
        return orderRepositoryPort.findById(orderId);
    }

    @Override
    public List<Order> getOrdersByCustomerId(String customerId) {
        return orderRepositoryPort.findByCustomer(customerId);
    }

    @Override
    public Order cancelOrder(UUID orderId) {
        Order order = orderRepositoryPort.findById(orderId).orElseThrow(()->new RuntimeException("Order not found"));

        order.cancel();

        //increase stock
        order.getItems().forEach(orderItem ->{
            productRepositoryPort.fidById(orderItem.getProductId()).ifPresent(product -> {
                product.increaseStock(orderItem.getQuantity());
                productRepositoryPort.save(product);
            });
        });

        //notification
        notificationPort.sendNotification(order.getCustomerId(), "Your order " + order.getId() + " has been cancelled.");

        return orderRepositoryPort.save(order);

    }
}
