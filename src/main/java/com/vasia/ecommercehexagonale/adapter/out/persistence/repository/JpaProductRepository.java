package com.vasia.ecommercehexagonale.adapter.out.persistence.repository;

import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaProductRepository extends JpaRepository<JpaProductEntity, UUID> {
}
