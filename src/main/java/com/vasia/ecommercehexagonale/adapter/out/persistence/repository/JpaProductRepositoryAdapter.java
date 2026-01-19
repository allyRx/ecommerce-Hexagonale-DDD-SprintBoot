package com.vasia.ecommercehexagonale.adapter.out.persistence.repository;

import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaProductEntity;
import com.vasia.ecommercehexagonale.domain.model.Product;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JpaProductRepositoryAdapter implements ProductRepositoryPort {
    private final JpaProductRepository jpaProductRepository;

    public JpaProductRepositoryAdapter(JpaProductRepository jpaProductRepository) {
        this.jpaProductRepository = jpaProductRepository;
    }

    @Override
    public Product save(Product product) {
        // 1. Domaine -> Entité JPA
        JpaProductEntity entity = JpaProductEntity.fromDomain(product);

        // 2. Persistance
        JpaProductEntity savedEntity = jpaProductRepository.save(entity);

        // 3. Entité JPA -> Domaine
        return JpaProductEntity.toDomain(savedEntity);

    }

    @Override
    public Optional<Product> fidById(UUID id) {
        return jpaProductRepository.findById(id).map(JpaProductEntity::toDomain);
    }

    @Override
    public List<Product> findAll() {
        return jpaProductRepository.findAll()
                .stream()
                .map(JpaProductEntity::toDomain)
                .toList();
    }

    @Override
    public void deleteById(UUID id) {
        jpaProductRepository.deleteById(id);
    }
}
