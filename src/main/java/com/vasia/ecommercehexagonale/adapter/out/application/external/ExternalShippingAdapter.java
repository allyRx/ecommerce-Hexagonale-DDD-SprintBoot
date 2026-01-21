package com.vasia.ecommercehexagonale.adapter.out.application.external;

import com.vasia.ecommercehexagonale.domain.port.out.ShippingServicePort;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ExternalShippingAdapter implements ShippingServicePort {

    @Override
    public BigDecimal calculateShippingCost(String address, BigDecimal orderTotal) {
        System.out.println("Calculating shipping cost for address: " + address + " and order total: " + orderTotal);
        return orderTotal.compareTo(BigDecimal.valueOf(100)) > 0 ? BigDecimal.ZERO : BigDecimal.valueOf(5.00);
    }

    @Override
    public String trackShipment(String trackingNumber) {
        System.out.println("Tracking shipment: " + trackingNumber);
        return "Shipped";
    }
}
