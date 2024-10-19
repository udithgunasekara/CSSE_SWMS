package com.csse.Service.Imp;

import com.csse.DTO.Payment;
import com.csse.Service.PaymentService;
import com.csse.repo.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String savePayment(Payment payment) throws ExecutionException, InterruptedException {
        return paymentRepository.savePayment(payment);
    }
}
