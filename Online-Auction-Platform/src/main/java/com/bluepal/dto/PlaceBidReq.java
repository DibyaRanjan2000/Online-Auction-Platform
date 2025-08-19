package com.bluepal.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//dto/BidDtos.java
@Data
public class PlaceBidReq {
	@NotNull
	@DecimalMin("0.01")
	BigDecimal amount;
}


