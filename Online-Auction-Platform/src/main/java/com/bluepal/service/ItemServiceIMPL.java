package com.bluepal.service;

import java.time.Instant;
import java.util.List;

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
	
	
//	public List<Item> live(Pageable pageable) {
//		return itemsRepository.findAll();
//	}


	private ItemRes toRes(Item item) {
		return ItemRes.builder().id(item.getId()).name(item.getName()).description(item.getDescription())
				.startingPrice(item.getStartingPrice()).auctionStart(item.getAuctionStart())
				.auctionEnd(item.getAuctionEnd()).status(item.getStatus().name()).seller(item.getSeller().getUsername())
				.imageUrl(item.getImageUrl()) // ✅ Map imageUrl here
				.build();
	}

	public Page<ItemRes> getLiveItems(Pageable pageable) {
		Page<Item> items = itemsRepository.findByStatus(Item.Status.LIVE, pageable);
		return items.map(item -> ItemRes.builder() // Use builder here to ensure all fields are populated
				.id(item.getId()).name(item.getName()).description(item.getDescription())
				.startingPrice(item.getStartingPrice()).auctionStart(item.getAuctionStart())
				.auctionEnd(item.getAuctionEnd()).status(item.getStatus().name()).seller(item.getSeller().getUsername())
				.imageUrl(item.getImageUrl()) // ✅ Include imageUrl in the response
				.build());
	}
	
	@Transactional
	public void deleteById(String username, Long id) {
	    Item item = itemsRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Item not found with id " + id));

	    // ✅ Only seller can delete their own item
	    if (!item.getSeller().getUsername().equals(username)) {
	        throw new RuntimeException("You are not allowed to delete this item");
	    }

	    itemsRepository.delete(item);
	}


}
