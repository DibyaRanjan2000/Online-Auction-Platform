package com.bluepal.controller;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluepal.dto.PurchaseDTO;
import com.bluepal.dto.PurchaseRequest;
import com.bluepal.entity.Item;
import com.bluepal.entity.Purchase;
import com.bluepal.entity.User;
import com.bluepal.repo.ItemRepository;
import com.bluepal.repo.PurchaseRepository;
import com.bluepal.repo.UserRepository;

import lombok.RequiredArgsConstructor;

//controller/PurchaseController.java
@RestController
@RequestMapping("/purchase")
@RequiredArgsConstructor
public class PurchaseController {
	private final PurchaseRepository purchases;
	
	private final ItemRepository itemRepository;
    private final UserRepository userRepository;

//	@GetMapping("/item/{itemId}")
//	public ResponseEntity<?> byItem(@PathVariable Long itemId) {
//		return purchases.findByItemId(itemId).<ResponseEntity<?>>map(ResponseEntity::ok)
//				.orElseGet(() -> ResponseEntity.notFound().build());
//	}
	
//	@GetMapping("/item/{itemId}")
//	public ResponseEntity<?> byItem(@PathVariable Long itemId) {
//	    return purchases.findByItemId(itemId)
//	        .map(purchase -> new PurchaseDTO(
//	            purchase.getId(),
//	            purchase.getItem().getId(),
//	            purchase.getItem().getName(),   
//	            purchase.getBuyer().getId(),
//	            purchase.getFinalPrice(),
//	            purchase.getPurchasedAt()
//	        ))
//	        .map(ResponseEntity::ok)
//	        .orElseGet(() -> ResponseEntity.notFound().build());
//	}
	
	@GetMapping("/item/{itemId}")
    public ResponseEntity<?> byItem(@PathVariable Long itemId) {
        return purchases.findByItemId(itemId)
            .map(purchase -> new PurchaseDTO(
                purchase.getId(),
                purchase.getItem().getId(),
                purchase.getItem().getName(),
                purchase.getBuyer().getId(),
                purchase.getFinalPrice(),
                purchase.getPurchasedAt()
            ))
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ POST to make a purchase
    @PostMapping
    public ResponseEntity<?> makePurchase(@RequestBody PurchaseRequest request) {
        Item item = itemRepository.findById(request.getItemId())
                .orElseThrow(() -> new RuntimeException("Item not found"));

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        // Check if item already purchased
        if (purchases.findByItemId(item.getId()).isPresent()) {
            return ResponseEntity.badRequest().body("Item already purchased");
        }

        Purchase purchase = Purchase.builder()
                .item(item)
                .buyer(buyer)
                .finalPrice(request.getFinalPrice())
                .purchasedAt(Instant.now())
                .build();

        Purchase saved = purchases.save(purchase);

        PurchaseDTO dto = new PurchaseDTO(
                saved.getId(),
                saved.getItem().getId(),
                saved.getItem().getName(),
                saved.getBuyer().getId(),
                saved.getFinalPrice(),
                saved.getPurchasedAt()
        );

        return ResponseEntity.ok(dto);
    }

}
