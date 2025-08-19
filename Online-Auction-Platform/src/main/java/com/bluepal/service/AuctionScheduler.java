package com.bluepal.service;

import java.time.Instant;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluepal.entity.Bid;
import com.bluepal.entity.Item;
import com.bluepal.entity.Purchase;
import com.bluepal.repo.BidRepository;
import com.bluepal.repo.ItemRepository;
import com.bluepal.repo.PurchaseRepository;
import com.bluepal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

//service/AuctionScheduler.java
@Service
@RequiredArgsConstructor
public class AuctionScheduler {
	private final ItemRepository items;
	private final BidRepository bids;
	private final PurchaseRepository purchases;
	private final UserRepository users;

	@Scheduled(fixedDelay = 10000) // every 10s
	@Transactional
	public void closeEndedAuctions() {
		Instant now = Instant.now();
		List<Item> toClose = items.findAll().stream()
				.filter(i -> i.getStatus() == Item.Status.LIVE && now.isAfter(i.getAuctionEnd())).toList();

		for (Item item : toClose) {
			List<Bid> itemBids = bids.findByItemOrderByAmountDesc(item.getId());
			if (itemBids.isEmpty()) {
				item.setStatus(Item.Status.ENDED);
			} else {
				Bid winner = itemBids.get(0);
				purchases.save(Purchase.builder().item(item).buyer(winner.getBidder()).finalPrice(winner.getAmount())
						.purchasedAt(now).build());
				item.setStatus(Item.Status.SOLD);
			}
			items.save(item);
		}
	}
}
