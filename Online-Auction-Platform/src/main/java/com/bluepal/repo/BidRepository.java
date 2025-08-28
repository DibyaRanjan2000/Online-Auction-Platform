package com.bluepal.repo;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bluepal.dto.BidRes;
import com.bluepal.entity.Bid;

public interface BidRepository extends JpaRepository<Bid, Long> {

	@Query("select b from Bid b where b.item.id = :itemId order by b.amount desc, b.createdAt asc")
	List<Bid> findByItemOrderByAmountDesc(Long itemId);

	@Query("select max(b.amount) from Bid b where b.item.id = :itemId")
	BigDecimal findHighestAmount(Long itemId);

	@Query("SELECT b FROM Bid b JOIN FETCH b.bidder WHERE b.item.id = :itemId ORDER BY b.amount DESC, b.createdAt ASC")
	List<Bid> findTopBidByItemId(@org.springframework.data.repository.query.Param("itemId") Long itemId,
			org.springframework.data.domain.Pageable pageable);

	List<Bid> findByItem_Id(Long itemId);
}
