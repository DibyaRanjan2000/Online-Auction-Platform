package com.bluepal.service;



import java.math.BigDecimal;

import com.bluepal.dto.BidRes;
import com.bluepal.dto.PlaceBidReq;

public interface BidService {
    BidRes placeBid(String username, Long itemId, PlaceBidReq req);
    public BigDecimal getTopAmount(Long itemId);
}
