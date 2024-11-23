package com.csse.PaymentFactory;

import com.csse.DTO.Payment;

public class BusinessPaymentFactory implements PaymentFactory {
    @Override
    public Payment createPayment(Payment payment) {
        payment.setAmount(10000.00); // Set business payment amount
        return payment;
    }


}