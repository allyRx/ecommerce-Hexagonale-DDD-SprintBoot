package com.vasia.ecommercehexagonale.adapter.out.persistence.repository;

import com.vasia.ecommercehexagonale.adapter.out.persistence.entity.JpaOrderEntity;
import com.vasia.ecommercehexagonale.domain.model.Order;
import com.vasia.ecommercehexagonale.domain.port.out.OrderRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class JpaOrderRepositoryAdapter implements OrderRepositoryPort {

    private final JpaOrderRepository jpaOrderRepository;

    public JpaOrderRepositoryAdapter(JpaOrderRepository jpaOrderRepository){
        this.jpaOrderRepository=jpaOrderRepository;
    }

    @Override
    public Order save(Order order) {
        JpaOrderEntity entity = JpaOrderEntity.fromDomain(order);
       JpaOrderEntity savedEntity =  jpaOrderRepository.save(entity);
       return JpaOrderEntity.toDomain(savedEntity);
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return jpaOrderRepository.findById(id).map(JpaOrderEntity::toDomain);
    }

    @Override
    public List<Order> findByCustomer(String customerId) {
        return jpaOrderRepository.findByCustomerId(customerId).stream().map(JpaOrderEntity::toDomain).collect(Collectors.toList());
    }
}
