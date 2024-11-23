package com.csse.PaymentFactory;

import com.csse.DTO.Payment;

public class HouseholderPaymentFactory implements PaymentFactory {
    @Override
    public Payment createPayment(Payment payment) {
        payment.setAmount(4000.00); // Set householder payment amount
        return payment;
    }
}
