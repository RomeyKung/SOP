package com.example.paymentsservice.data;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, String> {
    PaymentEntity findByPaymentId(String paymentId);

    PaymentEntity findByPaymentIdOrTitle(String productId, String title);
}
