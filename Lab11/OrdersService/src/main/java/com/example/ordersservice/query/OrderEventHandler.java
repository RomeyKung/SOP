package com.example.ordersservice.query;

import com.example.ordersservice.core.data.OrderEntity;
import com.example.ordersservice.core.data.OrderRepository;
import com.example.ordersservice.core.events.OrderCreatedEvent;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {
    private final OrderRepository orderRepository;

    public OrderEventHandler(OrderRepository orderRepository){
        this.orderRepository = orderRepository;
    }

    @EventHandler
    public void on(OrderCreatedEvent event){
        OrderEntity orderEntity = new OrderEntity();
        BeanUtils.copyProperties(event, orderEntity);
        orderRepository.save(orderEntity);
    }

}
