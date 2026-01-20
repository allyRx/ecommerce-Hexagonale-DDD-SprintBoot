package com.vasia.ecommercehexagonale.domain.port.out;

import com.vasia.ecommercehexagonale.domain.model.Cart;

import java.util.Optional;

public interface CartRepositoryPort {
    Cart save(Cart cart);
    Optional<Cart> findByCustomerId(String customerId);
    void delete(Cart cart);
}
