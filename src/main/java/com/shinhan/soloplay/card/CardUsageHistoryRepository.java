package com.shinhan.soloplay.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CardUsageHistoryRepository extends JpaRepository<CardUsageHistoryEntity, Long> {
    List<CardUsageHistoryEntity> findByUserCard_CardNum(String cardNum);
}
