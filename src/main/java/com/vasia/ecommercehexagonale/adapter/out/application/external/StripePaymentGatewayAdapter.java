package com.vasia.ecommercehexagonale.adapter.out.application.external;

import com.stripe.exception.StripeException;
import com.vasia.ecommercehexagonale.domain.port.out.PaymentGatewayPort;
import org.springframework.beans.factory.annotation.Value;
import com.stripe.Stripe;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class StripePaymentGatewayAdapter implements PaymentGatewayPort {

    public StripePaymentGatewayAdapter(@Value("${stripe.api.key}") String apikey){
        Stripe.apiKey = apikey;
    }

    @Override
    public String processPayment(BigDecimal amount, String customerId) {
        try{
            // Stripe attend le montant en centimes (ex: 10.00 EUR -> 1000 centimes)
            long amountInCents = amount.multiply(new BigDecimal(100)).longValue();

            PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
                    .setAmount(amountInCents)
                    .setCurrency("eur")
                    .setCustomer(customerId)
                    .setDescription("E-commerce order payement")
                    .build();

            PaymentIntent intent = PaymentIntent.create(params);
            System.out.println("Stripe PaymentIntent created: " + intent.getId());
            return intent.getId();
        }catch (StripeException e ){
            System.err.println("Stripe Payment Error: " + e.getMessage());
            throw new RuntimeException("Payment failed: " + e.getMessage());
        }
    }
}
