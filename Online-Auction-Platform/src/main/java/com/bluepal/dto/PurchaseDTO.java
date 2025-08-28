package com.bluepal.dto;

import java.math.BigDecimal;
import java.time.Instant;

public class PurchaseDTO {
	private Long id;
	private Long itemId;
	private String itemName;
	private Long buyerId;
	private BigDecimal finalPrice;
	private Instant purchasedAt;
	private String buyerUsername;
	public PurchaseDTO(Long id, Long itemId, String itemName, Long buyerId, BigDecimal finalPrice, Instant purchasedAt,
			String buyerUsername) {
		super();
		this.id = id;
		this.itemId = itemId;
		this.itemName = itemName;
		this.buyerId = buyerId;
		this.finalPrice = finalPrice;
		this.purchasedAt = purchasedAt;
		this.buyerUsername = buyerUsername;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getItemId() {
		return itemId;
	}
	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public Long getBuyerId() {
		return buyerId;
	}
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}
	public BigDecimal getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(BigDecimal finalPrice) {
		this.finalPrice = finalPrice;
	}
	public Instant getPurchasedAt() {
		return purchasedAt;
	}
	public void setPurchasedAt(Instant purchasedAt) {
		this.purchasedAt = purchasedAt;
	}
	public String getBuyerUsername() {
		return buyerUsername;
	}
	public void setBuyerUsername(String buyerUsername) {
		this.buyerUsername = buyerUsername;
	}
	
	

	}