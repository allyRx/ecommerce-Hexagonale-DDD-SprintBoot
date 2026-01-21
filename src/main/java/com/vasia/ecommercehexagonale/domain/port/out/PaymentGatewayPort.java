package com.vasia.ecommercehexagonale.domain.port.out;

import java.math.BigDecimal;

public interface PaymentGatewayPort {
    String processPayment(BigDecimal amount , String customerId);
}


