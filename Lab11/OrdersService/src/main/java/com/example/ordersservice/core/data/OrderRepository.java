package com.example.ordersservice.core.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<OrderEntity, String> {
    OrderEntity findByProductId(String productId);

    OrderEntity findByProductIdOrTitle(String productId, String title);

}
