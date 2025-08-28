//package com.bluepal.controller;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.bluepal.dto.AuctionStatusResponse;
//import com.bluepal.dto.BidRes;
//import com.bluepal.service.AuctionTracking;
//import com.bluepal.service.AuctionTrackingServiceIMPL;
//
//import lombok.RequiredArgsConstructor;
//
//@RestController
//@RequestMapping("/auctions")
//@RequiredArgsConstructor
//public class AuctionTrackingController {
//
//    
//    private final AuctionTracking trackingService;
//
//    // ✅ Get auction status
//    @GetMapping("/{itemId}/status")
//    public ResponseEntity<AuctionStatusResponse> trackAuction(@PathVariable Long itemId) {
//        return ResponseEntity.ok(trackingService.getAuctionStatus(itemId));
//    }
//
//    // ✅ Get bid history
//    @GetMapping("/{itemId}/bids")
//    public ResponseEntity<List<BidRes>> getBidHistory(@PathVariable Long itemId) {
//        return ResponseEntity.ok(trackingService.getBidHistory(itemId));
//    }
//}
//
