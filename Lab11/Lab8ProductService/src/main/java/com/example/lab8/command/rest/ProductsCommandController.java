package com.example.lab8.command.rest;


import com.example.lab8.command.CreateProductCommand;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductsCommandController {
    private final Environment env;
    private final CommandGateway commandGateway;

    @Autowired
    public ProductsCommandController(Environment env, CommandGateway commandGateway){
        this.env = env;
        this.commandGateway = commandGateway;
    }



    @PostMapping
    public String createProduct(@RequestBody CreateProductRestModel model){
        CreateProductCommand command = CreateProductCommand.builder()
                .productId(UUID.randomUUID().toString())
                .title(model.getTitle())
                .price(model.getPrice())
                .quantity(model.getQuantity())
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
