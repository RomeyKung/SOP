package com.example.ordersservice.core.event;

import com.example.ordersservice.query.rest.OrderStatus;
import lombok.Value;

@Value
public class OrderApproveEvent {
    public String orderId;
    public OrderStatus orderStatus = OrderStatus.APPROVED;
}
