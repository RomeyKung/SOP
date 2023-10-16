package com.example.ordersservice.command.rest;


import com.example.ordersservice.command.CreateOrderCommand;
import com.example.ordersservice.command.OrderStatus;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders") //ถ้า request path /orders มาใช้ class นี้
public class OrderCommandController {
    private final Environment env; //
    private final CommandGateway commandGateway;

    @Autowired
    public OrderCommandController(Environment env, CommandGateway commandGateway){
        this.env = env;
        this.commandGateway = commandGateway;
    }



    @PostMapping
    public String createProduct(@RequestBody CreateOrderRestModel model){
        CreateOrderCommand command = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId("27b95829-4f3f-4ddf-8983-151ba010e35b")
                .quantity(model.getQuantity())
                .productId(model.getProductId())
                .addressId(model.getAddressId())
                .orderStatus(OrderStatus.CREATED)
                .build();
        String result;
        try{
            result = commandGateway.sendAndWait(command);
        }catch (Exception e){
            result = e.getLocalizedMessage();
        }
        return result;
    }


}
