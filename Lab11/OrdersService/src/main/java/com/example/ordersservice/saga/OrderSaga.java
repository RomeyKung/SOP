package com.example.ordersservice.saga;

import com.example.core.ProductReservedEvent;
import com.example.core.ReserveProductCommand;
import com.example.core.commands.ProcessPaymentCommand;
import com.example.core.model.User;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.ordersservice.core.events.OrderCreatedEvent;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
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


}
