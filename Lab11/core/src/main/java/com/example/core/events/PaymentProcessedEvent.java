package com.example.core.events;

import lombok.Builder;
import lombok.Data;

@Data
public class PaymentProcessedEvent {
    private String orderId;
    private String paymentId;
}
