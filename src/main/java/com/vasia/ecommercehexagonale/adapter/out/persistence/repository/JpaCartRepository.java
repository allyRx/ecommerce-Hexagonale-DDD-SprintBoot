package com.vasia.ecommercehexagonale.adapter.out.repository;

import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaCartEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface JpaCartRepository extends JpaRepository<JpaCartEntity, UUID> {
    Optional<JpaCartEntity> findByCustomerId(String customerId);
}
