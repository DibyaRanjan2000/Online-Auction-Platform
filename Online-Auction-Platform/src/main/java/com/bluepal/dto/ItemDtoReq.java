package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

//dto/ItemDtos.java
@Data
public class ItemDtoReq {
	@NotBlank
	String name;
	String description;
	@NotNull
	@DecimalMin("0.01")
	BigDecimal startingPrice;
	String imageUrl;
	@NotNull
	Instant auctionEnd; // auctionStart = now
}

