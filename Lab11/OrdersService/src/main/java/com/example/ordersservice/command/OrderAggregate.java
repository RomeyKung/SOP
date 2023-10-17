package com.example.ordersservice.command;

import com.example.ordersservice.command.commands.ApproveOrderCommand;
import com.example.ordersservice.command.commands.RejectOrderCommand;
import com.example.ordersservice.core.events.OrderApprovedEvent;
import com.example.ordersservice.core.events.OrderCreatedEvent;
import com.example.ordersservice.core.events.OrderRejectEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {
    //โครงสร้างตาม CreateOrderCommand เพื่อเก็บ status ล่าสุด
    @AggregateIdentifier
    public  String orderId;
    private String userId;
    private String productId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate(){}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand){
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);
        AggregateLifecycle.apply(orderCreatedEvent);

    }

    @CommandHandler
    public OrderAggregate(ApproveOrderCommand approveOrderCommand){
        OrderApprovedEvent orderApprovedEvent = new OrderApprovedEvent(approveOrderCommand.getOrderId());
        AggregateLifecycle.apply(orderApprovedEvent);
    }
    @CommandHandler
    public OrderAggregate(RejectOrderCommand rejectOrderCommand){
        OrderRejectEvent orderRejectEvent = new OrderRejectEvent(
                rejectOrderCommand.getOrderId(),
                rejectOrderCommand.getReason()
        );
        AggregateLifecycle.apply(orderRejectEvent);
    }


    @EventSourcingHandler
    public void on(OrderCreatedEvent orderCreatedEvent){
        this.orderId = orderCreatedEvent.getOrderId();
        this.userId = orderCreatedEvent.getUserId();
        this.productId = orderCreatedEvent.getProductId();
        this.quantity = orderCreatedEvent.getQuantity();
        this.addressId = orderCreatedEvent.getAddressId();
        this.orderStatus = orderCreatedEvent.getOrderStatus();

    }

    @EventSourcingHandler
    public void on(OrderApprovedEvent orderApprovedEvent){
        this.orderStatus = orderApprovedEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectEvent orderRejectEvent){
        this.orderStatus = orderRejectEvent.getOrderStatus();
    }


}

