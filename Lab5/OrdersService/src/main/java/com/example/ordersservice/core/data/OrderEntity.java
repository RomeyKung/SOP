package com.example.ordersservice.core.data;

import com.example.ordersservice.query.rest.OrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="orders")
@Data
public class OrderEntity {
    @Id
    @Column(unique = true)
    public String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;
}
