package com.csse.Controller;

import com.csse.DTO.Payment;
import com.csse.Service.PaymentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/save")
    public ResponseEntity<String> savePayment(@RequestBody Payment payment) {
        try {
            String paymentId = paymentService.savePayment(payment);
            return ResponseEntity.status(HttpStatus.CREATED).body("Payment saved with ID: " + paymentId);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving payment");
        }
    }
}

