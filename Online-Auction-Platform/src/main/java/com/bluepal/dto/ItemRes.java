package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor   
@NoArgsConstructor    
public class ItemRes {
    private Long id;
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    private String status;
    private String seller;
    private String imageUrl;

    // Optional: Short constructor for partial mapping
    public ItemRes(Long id, String name, BigDecimal startingPrice, String status) {
        this.id = id;
        this.name = name;
        this.startingPrice = startingPrice;
        this.status = status;
    }

    @Override
    public String toString() {
        return "ItemRes [id=" + id + ", name=" + name + ", description=" + description +
               ", startingPrice=" + startingPrice + ", auctionStart=" + auctionStart +
               ", auctionEnd=" + auctionEnd + ", status=" + status +
               ", seller=" + seller + ", imageUrl=" + imageUrl + "]";
    }
}