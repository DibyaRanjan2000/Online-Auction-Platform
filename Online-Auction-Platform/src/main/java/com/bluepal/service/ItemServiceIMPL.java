package com.bluepal.service;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bluepal.dto.ItemDtoReq;
import com.bluepal.dto.ItemRes;
import com.bluepal.entity.Item;
import com.bluepal.entity.User;
import com.bluepal.repo.ItemRepository;
import com.bluepal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

//service/ItemService.java
@Service
@RequiredArgsConstructor
public class ItemServiceIMPL implements ItemService {
	private final ItemRepository itemsRepository;
	private final UserRepository users;

	@Transactional
	public ItemRes create(String username, ItemDtoReq req) {
		User seller = users.findByUsername(username).orElseThrow();
		Item item = Item.builder().name(req.getName()).description(req.getDescription())
				.startingPrice(req.getStartingPrice()).imageUrl(req.getImageUrl()).auctionStart(Instant.now())
				.auctionEnd(req.getAuctionEnd()).seller(seller).status(Item.Status.LIVE).build();
		itemsRepository.save(item);
		return toRes(item);
	}

	public Page<ItemRes> live(Pageable pageable) {
		return itemsRepository.findLive(pageable, Instant.now()).map(this::toRes);
	}

	private ItemRes toRes(Item i) {
		return ItemRes.builder().id(i.getId()).name(i.getName()).description(i.getDescription())
				.startingPrice(i.getStartingPrice()).auctionStart(i.getAuctionStart()).auctionEnd(i.getAuctionEnd())
				.status(i.getStatus().name()).seller(i.getSeller().getUsername()).build();
	}

	public Page<ItemRes> getLiveItems(Pageable pageable) {
	    Page<Item> items = itemsRepository.findByStatus(Item.Status.LIVE, pageable);
	    return items.map(item -> new ItemRes(
	            item.getId(),
	            item.getName(),
	            item.getStartingPrice(), // ✅ correct field
	            item.getStatus().name()
	    ));
	

    }
	
	
}
