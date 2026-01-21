package com.vasia.ecommercehexagonale.application.config;

import com.vasia.ecommercehexagonale.domain.port.out.CartRepositoryPort;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import com.vasia.ecommercehexagonale.domain.service.CartService;
import com.vasia.ecommercehexagonale.domain.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public ProductService productService(ProductRepositoryPort productRepositoryPort){
        return  new ProductService(productRepositoryPort);
    }

    @Bean
    public CartService cartService(CartRepositoryPort cartRepositoryPort, ProductRepositoryPort productRepositoryPort) {
        return new CartService(cartRepositoryPort, productRepositoryPort);
    }
}
