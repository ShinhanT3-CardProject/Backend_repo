package com.shinhan.soloplay.card;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CardUsageHistoryRepository extends JpaRepository<CardUsageHistoryEntity, Long>{

	@Query("SELECT SUM(cuh.amount) "
			+ "FROM CardUsageHistoryEntity cuh "
			+ "where cuh.userCard.cardNum IN ?1 "
			+ "and cuh.merchant.merchantId = ?2 "
			+ "and cuh.transactionDate >= ?3 "
			+ "and cuh.transactionDate <= ?4")
	int calculateAttack(List<String> cardNumList, String merchantId, Timestamp startTime, Timestamp endTime);
}
