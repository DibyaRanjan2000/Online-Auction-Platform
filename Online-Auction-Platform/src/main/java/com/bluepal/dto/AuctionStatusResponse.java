package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
public class AuctionStatusResponse {
    Long itemId;
    String itemName;
    BigDecimal highestBid;
    String highestBidder;
    Instant auctionEnd;
    String status;
	public AuctionStatusResponse(Long itemId, String itemName, BigDecimal highestBid, String highestBidder,
			Instant auctionEnd, String status) {
		super();
		this.itemId = itemId;
		this.itemName = itemName;
		this.highestBid = highestBid;
		this.highestBidder = highestBidder;
		this.auctionEnd = auctionEnd;
		this.status = status;
	}
	public AuctionStatusResponse(Long id, String name, String name2, BigDecimal currentHighestBid,
			String highestBidder2) {
		// TODO Auto-generated constructor stub
	}
    
    
    
}