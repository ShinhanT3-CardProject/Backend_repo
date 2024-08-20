package com.shinhan.soloplay.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.user.UserEntity;

public interface CouponHistoryRepository extends JpaRepository<CouponHistoryEntity, Long> {
	
	// 사용자가 보유한 모든 쿠폰 기록을 조회하는 메서드
    List<CouponHistoryEntity> findByUser(UserEntity user);
    
    // 사용 가능한 쿠폰 기록을 조회하는 메서드
    List<CouponHistoryEntity> findByUserAndIsUsed(UserEntity user, int isUsed);
    
    // 특정 쿠폰 기록을 사용된 것으로 업데이트하는 메서드
    @Modifying
    @Transactional
    @Query("UPDATE CouponHistoryEntity c SET c.isUsed = 1 WHERE c.couponHistoryId = :couponHistoryId")
    void markCouponAsUsed(Long couponHistoryId);
    
}
