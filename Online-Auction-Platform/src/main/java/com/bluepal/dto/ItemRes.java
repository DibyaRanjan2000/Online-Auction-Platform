package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
@Builder
@AllArgsConstructor   // generates full constructor
@NoArgsConstructor    // generates no-args constructor
public class ItemRes {
    private Long id;
    private String name;
    private String description;
    private BigDecimal startingPrice;
    private Instant auctionStart;
    private Instant auctionEnd;
    private String status;
    private String seller;
    

    // ✅ remove this if you want to always use builder/all-args
    // OR keep it only if you specifically need a short version
    public ItemRes(Long id, String name, BigDecimal startingPrice, String status) {
        this.id = id;
        this.name = name;
        this.startingPrice = startingPrice;
        this.status = status;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getStartingPrice() {
		return startingPrice;
	}

	public void setStartingPrice(BigDecimal startingPrice) {
		this.startingPrice = startingPrice;
	}

	public Instant getAuctionStart() {
		return auctionStart;
	}

	public void setAuctionStart(Instant auctionStart) {
		this.auctionStart = auctionStart;
	}

	public Instant getAuctionEnd() {
		return auctionEnd;
	}

	public void setAuctionEnd(Instant auctionEnd) {
		this.auctionEnd = auctionEnd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeller() {
		return seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Override
	public String toString() {
		return "ItemRes [id=" + id + ", name=" + name + ", description=" + description + ", startingPrice="
				+ startingPrice + ", auctionStart=" + auctionStart + ", auctionEnd=" + auctionEnd + ", status=" + status
				+ ", seller=" + seller + "]";
	}
	


	
    
}
