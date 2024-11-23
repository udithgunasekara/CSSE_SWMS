package com.csse.Service.Imp;

import com.csse.DTO.Payment;
import com.csse.PaymentFactory.BusinessPaymentFactory;
import com.csse.PaymentFactory.HouseholderPaymentFactory;
import com.csse.PaymentFactory.PaymentFactory;
import com.csse.Service.PaymentService;
import com.csse.repo.PaymentRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

@Service
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Override
    public String savePayment(Payment payment) throws ExecutionException, InterruptedException {
        PaymentFactory factory;

        // Decide which factory to use based on the role
        if ("business".equals(payment.getRole())) {
            factory = new BusinessPaymentFactory();
        } else if ("householder".equals(payment.getRole())) {
            factory = new HouseholderPaymentFactory();
        } else {
            throw new IllegalArgumentException("Invalid role: " + payment.getRole());
        }

        // Use the factory to create the correct payment object
        Payment processedPayment = factory.createPayment(payment);

        // Save the payment along with the userId
        processedPayment.setUserId(payment.getUserId());
        return paymentRepository.savePayment(processedPayment);
    }

}

