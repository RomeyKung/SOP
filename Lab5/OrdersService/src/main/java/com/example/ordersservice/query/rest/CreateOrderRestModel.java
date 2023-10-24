package com.example.ordersservice.query.rest;

import lombok.Data;

@Data
public class CreateOrderRestModel {
    private String productId;
    private Integer quantity;
    private String addressId;
    private OrderStatus orderStatus;
}
