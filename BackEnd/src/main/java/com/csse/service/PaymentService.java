package com.csse.Service;

import com.csse.DTO.Payment;

import java.util.concurrent.ExecutionException;

public interface PaymentService {
    String savePayment(Payment payment) throws ExecutionException, InterruptedException;
}
