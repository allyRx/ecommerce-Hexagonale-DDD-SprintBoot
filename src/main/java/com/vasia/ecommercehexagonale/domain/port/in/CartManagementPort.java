package com.vasia.ecommercehexagonale.domain.port.in;

import com.vasia.ecommercehexagonale.domain.model.Cart;

import java.util.UUID;

public interface CartManagementPort {

    Cart getCartByCustomerId(String customerId);
    Cart addProductToCart(String customerId, UUID productId, int quantity);
    Cart removeProductFromCart(String customerId, UUID productId);
    Cart updateProductQuantity(String customerId, UUID productId, int quantity);
    Cart applyDiscountCode(String customerId, String discountCode);

}
