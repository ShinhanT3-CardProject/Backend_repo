package com.shinhan.soloplay.card;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface CardUsageHistoryRepository extends JpaRepository<CardUsageHistoryEntity, Long> {
	@Query("SELECT cuh FROM #{#entityName} cuh WHERE cuh.userCard.cardNum = :cardNum")
	List<CardUsageHistoryEntity> findByUserCard_CardNum(String cardNum);
  
	// 레이드 전체 결제 내역 조회
	@Query("SELECT cuh "
			+ "FROM #{#entityName} cuh "
			+ "WHERE cuh.merchant.merchantId = :merchantId "
			+ "AND cuh.usageId > :usageId "
			+ "AND cuh.transactionDate >= :startTime "
			+ "AND cuh.transactionDate <= :endTime")
	List<CardUsageHistoryEntity> findByMerchantIdAndUsageId(String merchantId, Long usageId, Timestamp startTime, Timestamp endTime);

}
