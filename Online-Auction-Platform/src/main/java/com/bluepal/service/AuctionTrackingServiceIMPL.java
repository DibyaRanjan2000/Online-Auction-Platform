//package com.bluepal.service;
//
//import com.bluepal.dto.AuctionStatusResponse;
//import com.bluepal.dto.BidRes;
//import com.bluepal.entity.AuctionItem;
//import com.bluepal.entity.Bid;
//import com.bluepal.repo.AuctionRepository;
//
//import com.bluepal.service.AuctionTracking;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//@RequiredArgsConstructor
//public class AuctionTrackingServiceIMPL implements AuctionTracking {
//
//    private final AuctionRepository auctionRepository;
//    private final com.bluepal.repo.BidRepository bidRepository;
//
//    @Override
//    public AuctionStatusResponse getAuctionStatus(Long itemId) {
//        AuctionItem item = auctionRepository.findById(itemId)
//                .orElseThrow(() -> new RuntimeException("Auction not found"));
//
//        return new AuctionStatusResponse(
//                item.getId(),
//                item.getName(),
//                item.getStatus().name(),
//                item.getCurrentHighestBid(),
//                item.getHighestBidder()
//        );
//    }
//
//    @Override
//    public List<BidRes> getBidHistory(Long itemId) {
//        List<Bid> bids = bidRepository.findByItem_Id(itemId);
//        return bids.stream()
//                .map(b -> new BidRes(b.getId(), b.getAmount(), b.getBidder().getUsername(), b.getCreatedAt()))
//                .collect(Collectors.toList());
//    }
//}
