package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BidRes {
	Long itemId;
	BigDecimal highest;
	String highestBidder;
	Instant at;
}
