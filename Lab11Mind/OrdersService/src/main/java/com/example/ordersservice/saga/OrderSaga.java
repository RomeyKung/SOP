package com.example.ordersservice.saga;

import com.example.core.commands.CancelProductReservationCommand;
import com.example.core.commands.ProcessPaymentCommand;
import com.example.core.commands.ReserveProductCommand;
import com.example.core.events.PaymentProcessedEvent;
import com.example.core.events.ProductReservedEvent;
import com.example.core.model.User;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.ordersservice.command.ApproveOrderCommand;
import com.example.ordersservice.command.RejectOrderCommand;
import com.example.ordersservice.core.event.CreateOrderEvent;
import com.example.ordersservice.core.event.OrderApproveEvent;
import jakarta.inject.Inject;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.SagaLifecycle;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
public class OrderSaga {
    @Inject
    private transient CommandGateway commandGateway;
    @Inject
    private transient QueryGateway queryGateway;

    public OrderSaga() {}

    public OrderSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(CreateOrderEvent createOrderEvent) {
//        SagaLifecycle.associateWith("orderId", createOrderEvent.orderId);
        SagaLifecycle.associateWith("productId", createOrderEvent.getProductId());
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(createOrderEvent.getOrderId())
                .productId(createOrderEvent.getProductId())
                .quantity(createOrderEvent.getQuantity())
                .userId(createOrderEvent.getUserId())
                .build();

        // send reserveProductCommand ไปที่ product aggregate ถ้า qty ของ product ไม่พอ จะเกิด exception ขึ้น
        // แล้วจะส่ง reject order command
        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
            if (commandResultMessage.isExceptional()) {
                System.out.println("😺 Reject");
                RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(
                        createOrderEvent.getOrderId(),
                        commandResultMessage.exceptionResult().getMessage()
                );
                commandGateway.send(rejectOrderCommand);
            }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent) {
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        User user = null;

        try {
            user = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch (Exception e) {
            cancelProductReservationCommand(productReservedEvent, e.getMessage());
            return;
        }

        if (user == null) {
            cancelProductReservationCommand(productReservedEvent, "Could not fetch user payment details.");
            return;
        }

        System.out.println("😺 1");

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetail(user.getPaymentDetail())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;

        System.out.println("😺 2");

        try {
            System.out.println("😺 3");
            result = String.valueOf(commandGateway.send(processPaymentCommand));
        } catch (Exception e) {
            System.out.println("😺 4");
            cancelProductReservationCommand(productReservedEvent, e.getMessage());
            return;
        }

        if (result == null) {
            cancelProductReservationCommand(productReservedEvent, "Could not process user payment with provided payment details.");
        }
    }

    public void cancelProductReservationCommand(ProductReservedEvent productReservedEvent, String reason) {
        CancelProductReservationCommand cancelProductReservationCommand = CancelProductReservationCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .productId(productReservedEvent.getProductId())
                .quantity(productReservedEvent.getQuantity())
                .userId(productReservedEvent.getUserId())
                .reason(reason)
                .build();
        commandGateway.send(cancelProductReservationCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(PaymentProcessedEvent paymentProcessedEvent) {
        ApproveOrderCommand approveOrderCommand = ApproveOrderCommand.builder().orderId(paymentProcessedEvent.getOrderId()).build();
        commandGateway.send(approveOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApproveEvent orderApproveEvent) {}
}
