package com.csse.PaymentFactory;

import com.csse.DTO.Payment;

public interface PaymentFactory {
    Payment createPayment(Payment payment);
}
