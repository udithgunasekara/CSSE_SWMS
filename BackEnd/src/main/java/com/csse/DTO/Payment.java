package com.csse.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private String paymentID;
    private double amount;
    private Date paymentDate;
    private String paymentType;
    private String paymentMethod;

    // Getters and Setters
}
