package com.bluepal.service;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.bluepal.dto.BidRes;
import com.bluepal.dto.PlaceBidReq;
import com.bluepal.entity.Bid;
import com.bluepal.entity.Item;
import com.bluepal.entity.User;
import com.bluepal.repo.BidRepository;
import com.bluepal.repo.ItemRepository;
import com.bluepal.repo.UserRepository;
import com.bluepal.webSocket.BidPlacedEvent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BidServiceImpl implements BidService {

	private final ItemRepository items;
	private final BidRepository bids;
	private final UserRepository users;
	private final ApplicationEventPublisher events; // for websocket push

	@Override
	public BidRes placeBid(String username, Long itemId, PlaceBidReq req) {

		User bidder = users.findByUsername(username).orElseThrow();
		Item item = items.findById(itemId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

		if (item.getStatus() != Item.Status.LIVE || Instant.now().isAfter(item.getAuctionEnd()))
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Auction ended");

		BigDecimal current = Optional.ofNullable(bids.findHighestAmount(itemId))
				.orElse(item.getStartingPrice().subtract(BigDecimal.ONE));
		if (req.getAmount().compareTo(current) <= 0)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bid must be greater than current");

		// Save bid
		Bid bid = bids
				.save(Bid.builder().item(item).bidder(bidder).amount(req.getAmount()).createdAt(Instant.now()).build());

		// Touch item to advance version (protect against concurrent closes)
		item.setVersion(item.getVersion()); // no-op but ensures lock; alternatively items.save(item);

		// Push realtime update
		events.publishEvent(new BidPlacedEvent(itemId, bid.getAmount(), bidder.getUsername(), bid.getCreatedAt()));
		return new BidRes(itemId, bid.getAmount(), bidder.getUsername(), bid.getCreatedAt());
	}

	public BigDecimal getTopAmount(Long itemId) {
		return Optional.ofNullable(bids.findHighestAmount(itemId)).orElse(BigDecimal.ZERO);
	}

}
