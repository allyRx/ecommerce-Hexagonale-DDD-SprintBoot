package com.vasia.ecommercehexagonale.adapter.out.application.config;

import com.vasia.ecommercehexagonale.adapter.out.application.external.EmailNotificationAdapter;
import com.vasia.ecommercehexagonale.adapter.out.application.external.StripePaymentGatewayAdapter;
import com.vasia.ecommercehexagonale.adapter.out.application.external.StripeCustomerAdapter;
import com.vasia.ecommercehexagonale.adapter.out.persistence.repository.JpaCartAdapter;
import com.vasia.ecommercehexagonale.adapter.out.persistence.repository.JpaProductRepositoryAdapter;
import com.vasia.ecommercehexagonale.adapter.out.persistence.repository.JpaOrderRepository;
import com.vasia.ecommercehexagonale.domain.port.in.CartManagementPort;
import com.vasia.ecommercehexagonale.domain.port.in.ProductCatalogUseCase;
import com.vasia.ecommercehexagonale.domain.port.out.CartRepositoryPort;
import com.vasia.ecommercehexagonale.domain.port.out.NotificationPort;
import com.vasia.ecommercehexagonale.domain.port.out.PaymentGatewayPort;
import com.vasia.ecommercehexagonale.domain.port.out.PaymentCustomerPort;
import com.vasia.ecommercehexagonale.domain.port.out.ProductRepositoryPort;
import com.vasia.ecommercehexagonale.domain.service.CartService;
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

    @Bean
    public CartManagementPort cartManagementPort(CartService cartService)
    {
        return cartService;
    }
    @Bean
    public CartRepositoryPort cartRepositoryPort(JpaCartAdapter adapter) {
        return adapter;
    }

    @Bean
    public JpaOrderRepository orderRepository(JpaOrderRepository jpaOrderRepository){
        return jpaOrderRepository;
    }

    @Bean
    public PaymentGatewayPort paymentGatewayPort(StripePaymentGatewayAdapter adapter){return adapter;}

    @Bean
    public PaymentCustomerPort paymentCustomerPort(StripeCustomerAdapter adapter){return adapter;}

    @Bean
    public NotificationPort notificationPort(EmailNotificationAdapter adapter){return adapter;}
}
