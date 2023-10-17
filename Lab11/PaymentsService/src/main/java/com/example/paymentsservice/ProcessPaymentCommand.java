package com.example.paymentsservice;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProcessPaymentCommand {
    private final String orderId;
    private final String paymentId;
}
