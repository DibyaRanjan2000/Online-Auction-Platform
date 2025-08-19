package com.bluepal.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//entity/Item.java
@Entity
@Table(name = "items")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Item {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String name;
	@Column(length = 2000)
	private String description;
	@Column(nullable = false)
	private BigDecimal startingPrice;
	private String imageUrl;

	@Column(nullable = false)
	private Instant auctionStart;
	@Column(nullable = false)
	private Instant auctionEnd;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seller_id")
	private User seller;

	@Enumerated(EnumType.STRING)
	private Status status; // DRAFT, LIVE, ENDED, SOLD

	public enum Status {
		DRAFT, LIVE, ENDED, SOLD
	}

	@Version
	private Long version; // optimistic locking
}
