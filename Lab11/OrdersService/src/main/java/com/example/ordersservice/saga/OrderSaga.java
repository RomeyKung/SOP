package com.example.ordersservice.saga;

import com.example.core.events.ProductReservedEvent;
import com.example.core.commands.CancelProductReservationCommand;
import com.example.core.commands.ReserveProductCommand;
import com.example.core.commands.ProcessPaymentCommand;
import com.example.core.model.User;
import com.example.core.events.PaymentProcessedEvent;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.ordersservice.command.commands.ApproveOrderCommand;
import com.example.ordersservice.command.commands.RejectOrderCommand;
import com.example.ordersservice.core.events.OrderApprovedEvent;
import com.example.ordersservice.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;

import java.util.UUID;

public class OrderSaga {
    private final transient CommandGateway commandGateway;
    private transient QueryGateway queryGateway;

    public OrderSaga(CommandGateway commandGateway, QueryGateway queryGateway){
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent orderCreatedEvent){
        ReserveProductCommand reserveProductCommand = ReserveProductCommand.builder()
                .orderId(orderCreatedEvent.getOrderId())
                .productId(orderCreatedEvent.getProductId())
                .quantity(orderCreatedEvent.getQuantity())
                .userId(orderCreatedEvent.getUserId())
                .build();


        commandGateway.send(reserveProductCommand, (commandMessage, commandResultMessage) -> {
           if(commandResultMessage.isExceptional()){
               //Start aa compensating transaction
               RejectOrderCommand rejectOrderCommand = new RejectOrderCommand(
                       orderCreatedEvent.getOrderId(),
                       commandResultMessage.exceptionResult().getMessage()
               );
               commandGateway.send(rejectOrderCommand);
           }
        });
    }

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent productReservedEvent){
        //Process user payment
        FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery = new FetchUserPaymentDetailsQuery(productReservedEvent.getUserId());

        User user = null;


        try{
            user = queryGateway.query(fetchUserPaymentDetailsQuery, ResponseTypes.instanceOf(User.class)).join();
        }catch (Exception ex){
            //Start compensating transaction
            return;
        }
        if(user == null ){
            //Start compensating transaction
            return;
        }

        ProcessPaymentCommand processPaymentCommand = ProcessPaymentCommand.builder()
                .orderId(productReservedEvent.getOrderId())
                .paymentDetails(user.getPaymentDetails())
                .paymentId(UUID.randomUUID().toString())
                .build();

        String result = null;
        try{
            result = commandGateway.sendAndWait(processPaymentCommand);
        }catch (Exception ex){
            //Start compensating transaction
            return;
        }

        if(result == null){
            //Start compensating transaction

        }


    }
    private void cancelProductReservation(ProductReservedEvent productReservedEvent, String reason){
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
    public void handle(PaymentProcessedEvent paymentProcessedEvent){
        ApproveOrderCommand approveOrderCommand = new ApproveOrderCommand(paymentProcessedEvent.getOrderId());
        commandGateway.send(approveOrderCommand);
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderApprovedEvent orderApprovedEvent){

    }


}
