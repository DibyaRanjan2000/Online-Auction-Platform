package com.bluepal.dto;



import lombok.Data;
import java.math.BigDecimal;

@Data
public class PaymentRequest {
    private Long purchaseId;
    private BigDecimal amount;
}
