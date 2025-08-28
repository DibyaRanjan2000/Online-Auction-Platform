package com.bluepal.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bluepal.entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	Optional<Purchase> findByItemId(Long itemId);

	Optional<Purchase> findByRazorpayOrderId(String razorpayOrderId);
	
	 List<Purchase> findByBuyerUsername(String username);

}
