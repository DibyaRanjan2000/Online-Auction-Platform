package com.bluepal.entity;

import java.math.BigDecimal;
import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//entity/Purchase.java
@Entity
@Table(name = "purchases")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "item_id", unique = true)
	private Item item;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "buyer_id")
	private User buyer;

	@Column(nullable = false)
	private BigDecimal finalPrice;
	@Column(nullable = false)
	private Instant purchasedAt;
	
	@Column(name = "razorpay_order_id", unique = true)
    private String razorpayOrderId;
	
	private String itemName;
    private String buyerUsername;
	
	
//    private String razorpayPaymentId;
//    private String razorpaySignature;
}
