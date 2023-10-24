package com.example.ordersservice.command;

import com.example.core.commands.CancelProductReservationCommand;
import com.example.ordersservice.core.event.CreateOrderEvent;
import com.example.ordersservice.core.event.OrderApproveEvent;
import com.example.ordersservice.core.event.OrderRejectedEvent;
import com.example.ordersservice.query.rest.OrderStatus;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private int quantity;
    private String addressId;
    private OrderStatus orderStatus;

    public OrderAggregate() {}

    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {
        CreateOrderEvent createOrderEvent = new CreateOrderEvent();
        BeanUtils.copyProperties(createOrderCommand, createOrderEvent);
        AggregateLifecycle.apply(createOrderEvent);
    }

    @CommandHandler
    public void handle(ApproveOrderCommand approveOrderCommand) {
        OrderApproveEvent orderApproveEvent = new OrderApproveEvent(approveOrderCommand.getOrderId());
        AggregateLifecycle.apply(orderApproveEvent);
    }

    @CommandHandler
    public void handle(RejectOrderCommand rejectOrderCommand) {
        OrderRejectedEvent orderRejectedEvent = new OrderRejectedEvent(
                rejectOrderCommand.getOrderId(),
                rejectOrderCommand.getReason()
        );
        AggregateLifecycle.apply(orderRejectedEvent);
    }

    @EventSourcingHandler
    public void on(CreateOrderEvent createOrderEvent) {
        System.out.println("ON AGGREGATE ORDER");
        System.out.println(createOrderEvent.getOrderId());
        this.orderId = createOrderEvent.getOrderId();
        this.productId = createOrderEvent.getProductId();
        this.userId = createOrderEvent.getUserId();
        this.quantity = createOrderEvent.getQuantity();
        this.addressId = createOrderEvent.getAddressId();
        this.orderStatus = createOrderEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderApproveEvent orderApproveEvent) {
        this.orderStatus = orderApproveEvent.getOrderStatus();
    }

    @EventSourcingHandler
    public void on(OrderRejectedEvent orderRejectedEvent) {
        this.orderStatus = orderRejectedEvent.getOrderStatus();
    }
}
