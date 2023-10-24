package com.example.userservice.query.rest;

import com.example.core.model.PaymentDetail;
import com.example.core.query.FetchUserPaymentDetailsQuery;
import com.example.core.model.User;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserEventsHandler {
    @QueryHandler
    public User findUserPaymentDetail(FetchUserPaymentDetailsQuery fetchUserPaymentDetailsQuery) {
        PaymentDetail paymentDetail = PaymentDetail.builder()
                .cardNumber("123Card")
                .cvv("123")
                .name("Meow Meow")
                .validUntilMonth(12)
                .validUntilYear(2030)
                .build();

        User user = User.builder()
                .firstName("Meow")
                .lastName("Meow")
                .userId(fetchUserPaymentDetailsQuery.getUserId())
                .paymentDetail(paymentDetail)
                .build();

        return user;
    }
}
