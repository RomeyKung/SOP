package com.example.paymentsservice.data;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name="payments")
@Data
public class PaymentEntity implements Serializable  {

    @Serial
    private static final long serialVersionUID = -6157864917578165481L;

    @Id
    @Column(unique = true)
    private String paymentId;
    public String orderId;
}
