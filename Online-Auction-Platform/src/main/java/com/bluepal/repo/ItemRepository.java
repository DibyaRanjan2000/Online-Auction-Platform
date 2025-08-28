package com.bluepal.repo;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluepal.entity.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
//	@Query("select i from Item i where i.status='LIVE' and i.auctionEnd > :now")
	@Query("select i from Item i where i.status='LIVE' and i.auctionEnd > :now")
	Page<Item> findLive(Pageable pageable, Instant now);

	Page<Item> findByStatus(Item.Status status, Pageable pageable);

}
