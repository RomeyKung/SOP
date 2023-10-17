package com.example.paymentsservice.event;

import lombok.Data;

@Data
public class ProductCreatedEvent {
    private  String orderId;
    private  String paymentId;
}
