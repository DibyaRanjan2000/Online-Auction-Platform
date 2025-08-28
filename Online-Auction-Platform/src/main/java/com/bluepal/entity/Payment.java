package com.bluepal.entity;

import java.math.BigDecimal;
import java.time.Instant;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Purchase purchase;

    private BigDecimal amount;
    private String method; // e.g., "RAZORPAY"
    private String status; // e.g., "SUCCESS"
    private Instant paidAt;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
