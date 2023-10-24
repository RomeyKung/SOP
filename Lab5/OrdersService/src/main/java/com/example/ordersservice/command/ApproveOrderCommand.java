package com.example.ordersservice.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
public class ApproveOrderCommand {
    @TargetAggregateIdentifier
    public final String orderId;
}
