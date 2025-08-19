package com.bluepal.webSocket;

import java.math.BigDecimal;
import java.time.Instant;

//websocket/BidPlacedEvent.java
public record BidPlacedEvent(Long itemId, BigDecimal amount, String bidder, Instant at) {}

