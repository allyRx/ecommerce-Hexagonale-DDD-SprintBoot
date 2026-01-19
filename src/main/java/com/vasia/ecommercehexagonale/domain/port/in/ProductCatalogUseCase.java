package com.vasia.ecommercehexagonale.domain.port.in;

import com.vasia.ecommercehexagonale.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductCatalogUseCase {
    Product createProduct(String name , String description, BigDecimal price, int stockQuantity, String vendorId );
    Optional<Product> getProductById(UUID id);
    Product updateProduct(UUID id, String name, String description, BigDecimal price, int stockQuantity, String vendorId);
    List<Product> getAllProductById(UUID id);
    void deleteProduct(UUID id);
}
