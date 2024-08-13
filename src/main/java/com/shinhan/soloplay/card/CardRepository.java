package com.shinhan.soloplay.card;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface CardRepository extends JpaRepository<CardEntity, Integer>, QuerydslPredicateExecutor<CardEntity>{
	 

}
