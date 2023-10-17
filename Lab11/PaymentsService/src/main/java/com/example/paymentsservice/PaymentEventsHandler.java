package com.example.paymentsservice;

import com.example.paymentsservice.data.PaymentEntity;
import com.example.paymentsservice.data.PaymentRepository;
import com.example.paymentsservice.event.ProductCreatedEvent;
import jakarta.persistence.Column;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

@Component
public class PaymentEventsHandler {
    private final PaymentRepository paymentRepository;

    public PaymentEventsHandler(PaymentRepository paymentRepository){
        this.paymentRepository = paymentRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event){
        PaymentEntity paymentEntity = new PaymentEntity();
        BeanUtils.copyProperties(event, paymentEntity);
        paymentRepository.save(paymentEntity);

    }


}
