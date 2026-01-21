package com.vasia.ecommercehexagonale.domain.service;

import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.in.CartManagementPort;
import com.vasia.ecommercehexagonale.domain.port.out.CartRepositoryPort;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import jakarta.transaction.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

public class CartService implements CartManagementPort {

    private  CartRepositoryPort cartRepositoryPort;
    private  ProductRepositoryPort productRepositoryPort;

    public CartService(CartRepositoryPort cartRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        this.cartRepositoryPort = cartRepositoryPort;
        this.productRepositoryPort = productRepositoryPort;
    }

    @Override
    public Cart getCartByCustomerId(String customerId) {
        return cartRepositoryPort.findByCustomerId(customerId)
                .orElseGet(() -> new Cart(customerId));
    }

    @Override
    @Transactional
    public Cart addProductToCart(String customerId, UUID productId, int quantity) {
        Cart cart = getCartByCustomerId(customerId);

        Product product = productRepositoryPort.fidById(productId).orElseThrow(() -> new IllegalArgumentException("Product not found."));

        // Logique métier : vérification de stock (simplifiée ici, pluscomplexe dans OrderService)
        if (!product.isAvailable(quantity)) {
            throw new IllegalArgumentException("Not enough stock for product " + product.getName());
        }

        cart.addItem(product, quantity);

        return cartRepositoryPort.save(cart);
    }

    @Override
    @Transactional
    public Cart removeProductFromCart(String customerId, UUID productId) {
        Cart cart = getCartByCustomerId(customerId);
        cart.removeItem(productId);
        return cartRepositoryPort.save(cart);
    }

    @Override
    @Transactional
    public Cart updateProductQuantity(String customerId, UUID productId, int quantity) {
        Cart cart = getCartByCustomerId(customerId);
        cart.updateItemQuantity(productId, quantity);
        return cartRepositoryPort.save(cart);
    }

    @Override
    public Cart applyDiscountCode(String customerId, String discountCode) {
        Cart cart = getCartByCustomerId(customerId);

        // Logique de vérification du code promo (simplifiée)
        if ("SUMMER20".equals(discountCode)) {
            cart.applyDiscount(discountCode, new BigDecimal("20"));
        } else {
            throw new IllegalArgumentException("Invalid discount code.");
        }

        return cartRepositoryPort.save(cart);
    }

    @Override
    public Cart getOrCreateCart(String customerId) {
        return cartRepositoryPort.findByCustomerId(customerId)
                .orElseGet(() -> cartRepositoryPort.save(new Cart(customerId)));
    }

    @Override
    public void clearCart(String customerId) {
        Cart cart = getOrCreateCart(customerId);

    }
}
