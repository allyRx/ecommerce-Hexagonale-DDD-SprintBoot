package com.vasia.ecommercehexagonale.application.config;

import com.vasia.ecommercehexagonale.adapter.out.persistence.repository.JpaProductRepositoryAdapter;
import com.vasia.ecommercehexagonale.domain.port.in.ProductCatalogUseCase;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import com.vasia.ecommercehexagonale.domain.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AdapterConfig {

    // 1. Lier le Port d'Entrée (Interface) à son implémentation (Service deDomaine)
    @Bean
    public ProductCatalogUseCase productCatalogUseCase(ProductService productService){
        return productService;
    }

    // 2. Lier le Port de Sortie (Interface) à son implémentation (Adapterde Persistance)
    @Bean
    public ProductRepositoryPort productRepositoryPort(JpaProductRepositoryAdapter jpaProductRepositoryAdapter){
        return jpaProductRepositoryAdapter;
    }
}
