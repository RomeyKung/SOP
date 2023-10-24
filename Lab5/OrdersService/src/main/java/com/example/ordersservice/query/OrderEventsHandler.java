package com.example.ordersservice.query;

import com.example.ordersservice.core.data.OrderEntity;
import com.example.ordersservice.core.data.OrderRepository;
import com.example.ordersservice.core.event.CreateOrderEvent;
import com.example.ordersservice.core.event.OrderApproveEvent;
import com.example.ordersservice.core.event.OrderRejectedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderEventsHandler {
    private final OrderRepository orderRepository;

    public OrderEventsHandler(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(CreateOrderEvent event) {
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderApproveEvent orderApproveEvent) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderApproveEvent.getOrderId());
        if (orderEntity == null) {
            return;
        }
        orderEntity.setOrderStatus(orderApproveEvent.getOrderStatus());
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectedEvent event) {
        OrderEntity orderEntity = orderRepository.findByOrderId(event.getOrderId());
        if (orderEntity == null) {
            return;
        }
        orderEntity.setOrderStatus(event.getOrderStatus());
        orderRepository.save(orderEntity);
    }
}
