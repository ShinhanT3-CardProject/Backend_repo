package com.shinhan.soloplay.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<CouponEntity, Long>{
	//쿠폰 ID를 기준으로 쿠폰 엔티티 목록을 조회
	List<CouponEntity> findByCouponIdIn(List<Long> couponIds);
}
