package com.bluepal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long purchaseId;
    private BigDecimal amount;
    private String method;
    private String status;
    private Instant paidAt;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
