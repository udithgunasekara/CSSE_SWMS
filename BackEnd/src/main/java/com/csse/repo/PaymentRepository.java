package com.csse.repo;

import com.csse.DTO.Payment;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

@Repository
public class PaymentRepository {
    private final Firestore firestore;
    private static final String PAYMENT_COLLECTION_NAME = "payments";

    public PaymentRepository(Firestore firestore) {
        this.firestore = firestore;
    }

    public String savePayment(Payment payment) throws ExecutionException, InterruptedException {
        String paymentId = generateUniquePaymentId(); // Method to auto-generate payment ID
        payment.setPaymentID(paymentId);
        DocumentReference docRef = firestore.collection(PAYMENT_COLLECTION_NAME).document(paymentId);
        ApiFuture<WriteResult> result = docRef.set(payment);
        result.get();
        return paymentId;
    }

    private String generateUniquePaymentId() {
        return UUID.randomUUID().toString(); // Generates a unique payment ID
    }
}

