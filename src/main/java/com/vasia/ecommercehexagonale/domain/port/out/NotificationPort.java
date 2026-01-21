package com.vasia.ecommercehexagonale.domain.port.out;

public interface NotificationPort {
    void sendNotification(String customerId ,String  message);
}
