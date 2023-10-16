package com.example.ordersservice.command.rest;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateOrderRestModel {
    private String productId;
    private int quantity;
    private String addressId;
}
