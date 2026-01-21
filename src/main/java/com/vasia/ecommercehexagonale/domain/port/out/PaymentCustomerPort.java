package com.vasia.ecommercehexagonale.domain.port.out;

/**
 * Port for managing customer creation and retrieval in the payment gateway
 */
public interface PaymentCustomerPort {
    /**
     * Creates or retrieves a customer in the payment system
     * @param customerId the customer identifier
     * @param email the customer email
     * @return the payment system customer ID
     */
    String createOrRetrieveCustomer(String customerId, String email);

    /**
     * Checks if a customer exists in the payment system
     * @param stripeCustomerId the Stripe customer ID
     * @return true if customer exists
     */
    boolean customerExists(String stripeCustomerId);
}
