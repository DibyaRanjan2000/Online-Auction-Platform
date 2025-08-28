//package com.bluepal.entity;
//
//import java.math.BigDecimal;
//import java.time.Instant;
//
////import com.bluepal.enums.AuctionStatus;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EnumType;
//import jakarta.persistence.Enumerated;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//import lombok.Data;
//
//@Entity
//@Data
//@Table(name = "items")
//public class AuctionItem {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private String name;
//    private BigDecimal startingPrice;
//    private BigDecimal currentHighestBid;
//    private String highestBidder;
//
//    private Instant auctionStart;
//    private Instant auctionEnd;
//
//    @Enumerated(EnumType.STRING)
//    private Item.Status status; // maps directly to items.status
// // OPEN, CLOSED
//}
//
