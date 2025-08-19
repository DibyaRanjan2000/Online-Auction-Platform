package com.bluepal.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bluepal.service.AuctionScheduler;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
    
    private final AuctionScheduler auctionScheduler;

    @PostMapping("/close-auctions")
    public ResponseEntity<String> triggerCloseAuctions() {
        auctionScheduler.closeEndedAuctions();
        return ResponseEntity.ok("Auction close process triggered manually.");
    }
}
