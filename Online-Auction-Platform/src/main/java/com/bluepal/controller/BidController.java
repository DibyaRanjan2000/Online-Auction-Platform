package com.bluepal.controller;

import java.math.BigDecimal;
import java.time.Instant;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluepal.dto.BidRes;
import com.bluepal.dto.PlaceBidReq;
import com.bluepal.service.BidService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//controller/BidController.java
@RestController
@RequestMapping("/bids")
@RequiredArgsConstructor
public class BidController {
	private final BidService bidService;

	@PostMapping("/items/{itemId}")
	public BidRes bid(@AuthenticationPrincipal String username, @PathVariable Long itemId,
			@Valid @RequestBody PlaceBidReq req) {
		return bidService.placeBid(username, itemId, req);
	}

	@GetMapping("/items/{itemId}/top")
	public BidRes top(@PathVariable Long itemId) {
		// quick read-only endpoint
		BigDecimal max = bidService.getTopAmount(itemId);
		return new BidRes(itemId, max, null, Instant.now());
	}
}
