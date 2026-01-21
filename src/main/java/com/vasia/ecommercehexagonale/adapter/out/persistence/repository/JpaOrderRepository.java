package com.vasia.ecommercehexagonale.adapter.out.persistence.repository;

import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaOrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JpaOrderRepository extends JpaRepository<JpaOrderEntity , UUID> {
    List<JpaOrderEntity> findByCustomerId(String customerId);
}
