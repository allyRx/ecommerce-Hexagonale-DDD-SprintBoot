package com.vasia.ecommercehexagonale.adapter.out.application.external;

import com.stripe.exception.StripeException;
import com.stripe.model.Customer;
import com.stripe.param.CustomerCreateParams;
import com.stripe.param.CustomerListParams;
import com.vasia.ecommercehexagonale.domain.port.out.PaymentCustomerPort;
import org.springframework.beans.factory.annotation.Value;
import com.stripe.Stripe;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Adapter for managing Stripe customers
 */
@Component
public class StripeCustomerAdapter implements PaymentCustomerPort {

    public StripeCustomerAdapter(@Value("${stripe.api.key}") String apikey) {
        Stripe.apiKey = apikey;
    }

    @Override
    public String createOrRetrieveCustomer(String customerId, String email) {
        try {
            // Try to find existing customer by email
            CustomerListParams listParams = CustomerListParams.builder()
                    .setEmail(email)
                    .setLimit(1L)
                    .build();

            Object response = Customer.list(listParams);
            
            // Get data from the list response
            if (response instanceof com.stripe.model.StripeCollection) {
                com.stripe.model.StripeCollection<?> collection = (com.stripe.model.StripeCollection<?>) response;
                if (!collection.getData().isEmpty()) {
                    Customer existingCustomer = (Customer) collection.getData().get(0);
                    System.out.println("Customer found in Stripe: " + existingCustomer.getId());
                    return existingCustomer.getId();
                }
            }

            // Create new customer if not found
            Map<String, String> metadata = new HashMap<>();
            metadata.put("customerId", customerId);

            CustomerCreateParams params = CustomerCreateParams.builder()
                    .setEmail(email)
                    .setMetadata(metadata)
                    .setDescription("Customer: " + customerId)
                    .build();

            Customer newCustomer = Customer.create(params);
            System.out.println("New Stripe customer created: " + newCustomer.getId());
            return newCustomer.getId();

        } catch (StripeException e) {
            System.err.println("Stripe Customer Error: " + e.getMessage());
            throw new RuntimeException("Failed to create or retrieve customer: " + e.getMessage());
        }
    }

    @Override
    public boolean customerExists(String stripeCustomerId) {
        try {
            Customer customer = Customer.retrieve(stripeCustomerId);
            return customer != null && customer.getId() != null;
        } catch (StripeException e) {
            System.err.println("Customer retrieval failed: " + e.getMessage());
            return false;
        }
    }
}
