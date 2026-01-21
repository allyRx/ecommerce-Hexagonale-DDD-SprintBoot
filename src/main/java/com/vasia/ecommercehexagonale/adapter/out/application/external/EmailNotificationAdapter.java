package com.vasia.ecommercehexagonale.adapter.out.application.external;

import com.vasia.ecommercehexagonale.domain.port.out.NotificationPort;
import org.springframework.stereotype.Component;

@Component
public class EmailNotificationAdapter implements NotificationPort {

    @Override
    public void sendNotification(String customerId, String message) {
        System.out.println("Sending email to " + customerId + ": " + message);
    }
}
