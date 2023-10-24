package com.example.ordersservice.core.event;

import com.example.ordersservice.query.rest.OrderStatus;
import lombok.Data;
import lombok.Value;

@Data
public class CreateOrderEvent {
    public String orderId;
    private String userId;
    private String productId;
    private Integer quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
