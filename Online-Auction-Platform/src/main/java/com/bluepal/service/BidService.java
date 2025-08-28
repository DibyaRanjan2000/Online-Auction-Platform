package com.bluepal.service;

import java.math.BigDecimal;
import java.util.List;

import com.bluepal.dto.BidRes;
import com.bluepal.dto.PlaceBidReq;
import com.bluepal.entity.Bid;

public interface BidService {
	BidRes placeBid(String username, Long itemId, PlaceBidReq req);

	public BigDecimal getTopAmount(Long itemId);

	public Bid getTopBid(Long itemId);
	// public BidRes placeBid1(Long itemId, BigDecimal amount, String bidder) ;
	
	public List<Bid> getBidsForItem(Long itemId);

}
