package com.bluepal.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bluepal.dto.ItemDtoReq;
import com.bluepal.dto.ItemRes;
import com.bluepal.dto.RegisterReq;
import com.bluepal.dto.UserDto;
import com.bluepal.repo.ItemRepository;
import com.bluepal.service.ItemService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

//controller/ItemController.java
@RestController
@RequestMapping("/items")
@RequiredArgsConstructor
public class ItemController {
	private final ItemService service;

	private final ItemRepository itemRepository;

	@PostMapping
	public ItemRes create(@AuthenticationPrincipal String username, @Valid @RequestBody ItemDtoReq req) {
		return service.create(username, req);
	}

	@GetMapping("/live")
	public Page<ItemRes> live(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
		return service.live(PageRequest.of(page, size, Sort.by("auctionEnd").ascending()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ItemRes> getItemById(@PathVariable Long id) {
		return itemRepository.findById(id)
				.map(item -> new ItemRes(item.getId(), item.getName(), item.getStartingPrice(), // maps price
						item.getStatus().name() // maps enum to String
				)).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	
	@GetMapping("/lives")
	public Page<ItemRes> getLiveItems(Pageable pageable) {
		return service.getLiveItems(pageable);
	}
}
