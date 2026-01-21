package com.vasia.ecommercehexagonale.domain.port.out;

import com.vasia.ecommercehexagonale.domain.model.Cart;

import java.util.Optional;
import java.util.UUID;

public interface CartRepositoryPort {
    Cart save(Cart cart);

    Optional<Cart> findById(UUID id);


    Optional<Cart> findByCustomerId(String customerId);

    void deleteById(UUID id);
}
