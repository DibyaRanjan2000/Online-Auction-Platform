package com.bluepal.webSocket;

import java.util.Map;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//websocket/BidEventsListener.java
@Component
@RequiredArgsConstructor
public class BidEventsListener {
	private final SimpMessagingTemplate template;

	@EventListener
	public void onBidPlaced(BidPlacedEvent evt) {
		Map<String, Object> payload = Map.of("itemId", evt.itemId(), "amount", evt.amount(), "bidder", evt.bidder(),
				"at", evt.at().toString());
		template.convertAndSend("/topic/auctions/" + evt.itemId(), payload);
	}
}
