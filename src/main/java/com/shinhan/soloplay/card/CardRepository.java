package com.shinhan.soloplay.card;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CardRepository extends JpaRepository<CardEntity, Long>, QuerydslPredicateExecutor<CardEntity>{
	// 카드 타입(테마 대분류명)으로 카드들을 조회
    List<CardEntity> findByCardType(String cardType);

}
