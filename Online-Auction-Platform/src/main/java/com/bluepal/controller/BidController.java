package com.bluepal.controller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluepal.dto.BidRes;
import com.bluepal.dto.PlaceBidReq;
import com.bluepal.entity.Bid;
import com.bluepal.repo.BidRepository;
import com.bluepal.service.BidService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//controller/BidController.java
@RestController
//@RequestMapping("/bids")
@RequestMapping("/bids")
@RequiredArgsConstructor
@CrossOrigin
public class BidController {
	private final BidService bidService;
	private final SimpMessagingTemplate simpMessagingTemplate;
	
	
	private final BidRepository bidRepository;

	@GetMapping("/history")
	public ResponseEntity<List<BidRes>> getBidHistory() {
	    List<BidRes> history = bidRepository.findAll().stream()
	            .map(b -> new BidRes(
	                    b.getItem().getId(),            // itemId
	                    b.getAmount(),                  // highest (bid amount)
	                    b.getBidder().getUsername(),    // highestBidder (actually this bid's user)
	                    b.getCreatedAt(),               // at
	                    b.getItem().getName(),          // item name
	                    b.getItem().getImageUrl()       // image url
	            ))
	            .toList(); // if < Java 16, use .collect(Collectors.toList())

	    return ResponseEntity.ok(history);
	}


	@PostMapping("/items/{itemId}")
	public BidRes bid(@AuthenticationPrincipal String username, @PathVariable Long itemId,
			@Valid @RequestBody PlaceBidReq req) {
		return bidService.placeBid(username, itemId, req);
	}

//	@GetMapping("/items/{itemId}/top")
//	public BidRes top(@PathVariable Long itemId) {
//		// quick read-only endpoint
//		BigDecimal max = bidService.getTopAmount(itemId);
//		return new BidRes(itemId, max, null, Instant.now());
//	}
	@GetMapping("/items/{itemId}/top")
	public BidRes top(@PathVariable Long itemId) {
		Bid topBid = bidService.getTopBid(itemId);

		if (topBid != null) {
			String bidderName = topBid.getBidder().getUsername(); // or getName(), depending on your User entity
			return new BidRes(itemId, topBid.getAmount(), bidderName, topBid.getCreatedAt(), topBid.getItem().getName(),
					topBid.getItem().getImageUrl());
		} else {
			return new BidRes(itemId, BigDecimal.ZERO, "No bids yet", Instant.now());
		}
	}

	@PostMapping("/items/lives/{itemId}")
	public BidRes bidLive(@AuthenticationPrincipal String username, @PathVariable Long itemId,
			@Valid @RequestBody PlaceBidReq req) {
		System.out.println("BidController.bidLive()1" + itemId);

		BidRes bidRes = bidService.placeBid(username, itemId, req);
		System.out.println("BidController.bidLive()2" + itemId);

//		// Broadcast the new bid to clients subscribed to /topic/bids/{itemId}
//		simpMessagingTemplate.convertAndSend("/topic/bids/" + itemId, bidRes);
//		System.out.println("BidController.bidLive()3"+itemId);
//
//		return bidRes;
		Bid topBid = bidService.getTopBid(itemId);
		if (topBid != null) {
			bidRes = new BidRes(itemId, topBid.getAmount(), topBid.getBidder().getUsername(), topBid.getCreatedAt(),
					topBid.getItem().getName(), topBid.getItem().getImageUrl());
		}

		simpMessagingTemplate.convertAndSend("/topic/bids/" + itemId, bidRes);
		return bidRes;
	}

	@GetMapping("/items/{itemId}/history")
	public ResponseEntity<?> getBidHistory(@PathVariable Long itemId) {
		List<Bid> bidList = bidService.getBidsForItem(itemId);

		List<BidRes> history = bidList.stream()
				.map(bid -> new BidRes(bid.getItem().getId(), bid.getAmount(), bid.getBidder().getUsername(),
						bid.getCreatedAt(), bid.getItem().getName(), // 👈 from Item entity
						bid.getItem().getImageUrl() // 👈 from Item entity
				)).toList();

		return ResponseEntity.ok(history);
	}

}