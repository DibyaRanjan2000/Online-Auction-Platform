package com.bluepal.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluepal.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

	@Query("select b from Bid b where b.item.id = :itemId order by b.amount desc, b.createdAt asc")
	List<Bid> findByItemOrderByAmountDesc(Long itemId);

	@Query("select max(b.amount) from Bid b where b.item.id = :itemId")
	BigDecimal findHighestAmount(Long itemId);

}
