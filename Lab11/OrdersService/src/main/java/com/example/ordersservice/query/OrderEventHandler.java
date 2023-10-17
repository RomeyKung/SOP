package com.example.ordersservice.query;

import com.example.ordersservice.core.data.OrderEntity;
import com.example.ordersservice.core.data.OrderRepository;
import com.example.ordersservice.core.events.OrderApprovedEvent;
import com.example.ordersservice.core.events.OrderCreatedEvent;
import com.example.ordersservice.core.events.OrderRejectEvent;
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

    @EventHandler
    public void on(OrderApprovedEvent event){
        OrderEntity orderEntity = orderRepository.findByProductId(event.getOrderId());
        if(orderEntity == null){
            return;
        }

        orderEntity.setOrderStatus(event.getOrderStatus());
        orderRepository.save(orderEntity);
    }

    @EventHandler
    public void on(OrderRejectEvent event){
        OrderEntity orderEntity = orderRepository.findByProductId(event.getOrderId());
        if(orderEntity == null){
            return;
        }
        orderEntity.setOrderStatus(event.getOrderStatus());
        orderRepository.save((orderEntity));
    }

}
