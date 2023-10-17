package com.example.ordersservice.core.events;

import com.example.ordersservice.command.OrderStatus;
import lombok.Value;

@Value
public class OrderRejectEvent {
    String orderId;
    String reason;
    OrderStatus orderStatus = OrderStatus.REJECTED;
}
