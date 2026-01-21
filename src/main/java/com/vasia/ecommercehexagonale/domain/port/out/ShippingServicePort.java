package com.vasia.ecommercehexagonale.domain.port.out;

import java.math.BigDecimal;

public interface ShippingServicePort {
    BigDecimal calculateShippingCost(String address, BigDecimal orderTotal);

    String trackShipment(String trackingNumber);
}
