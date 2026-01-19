package com.vasia.ecommercehexagonale.domain.port.out;

import com.vasia.ecommercehexagonale.domain.model.Product;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepositoryPort {
    Product  save(Product product);
    Optional<Product> fidById(UUID id);
    List<Product>findAll();
    void deleteById(UUID id);
}
