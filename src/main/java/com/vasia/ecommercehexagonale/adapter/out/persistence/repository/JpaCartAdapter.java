package com.vasia.ecommercehexagonale.adapter.out.persistence.repository;


import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaCartEntity;
import com.vasia.ecommercehexagonale.domain.model.Cart;
import com.vasia.ecommercehexagonale.domain.port.out.CartRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class JpaCartAdapter implements CartRepositoryPort {
    private final JpaCartRepository jpaCartRepository;

    public JpaCartAdapter(JpaCartRepository jpaCartRepository) {
        this.jpaCartRepository = jpaCartRepository;
    }

    @Override
    public Cart save(Cart cart) {
        JpaCartEntity entity = JpaCartEntity.fromDomain(cart);
        JpaCartEntity savedEntity = jpaCartRepository.save(entity);
        return JpaCartEntity.toDomain(savedEntity);
    }

    @Override
    public Optional<Cart> findById(UUID id) {
        return jpaCartRepository.findById(id)
                .map(JpaCartEntity::toDomain);
    }

    @Override
    public Optional<Cart> findByCustomerId(String customerId) {
        return jpaCartRepository.findByCustomerId(customerId)
                .map(JpaCartEntity::toDomain);
    }


    @Override
    public void deleteById(UUID id) {
        jpaCartRepository.deleteById(id);
    }
}
