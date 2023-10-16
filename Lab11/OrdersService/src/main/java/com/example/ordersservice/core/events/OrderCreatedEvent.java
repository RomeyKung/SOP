package com.example.ordersservice.core.events;

import lombok.Data;
import com.example.ordersservice.command.OrderStatus;

@Data
public class OrderCreatedEvent {
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
