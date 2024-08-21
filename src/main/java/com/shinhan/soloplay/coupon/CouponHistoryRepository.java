package com.shinhan.soloplay.coupon;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.user.UserEntity;

public interface CouponHistoryRepository extends JpaRepository<CouponHistoryEntity, Long> {
    
    // 사용 가능한 쿠폰 기록을 조회하는 메서드
	@Query("SELECT c FROM CouponHistoryEntity c WHERE c.user = :user AND c.isUsed = 1 AND date(c.expirationDate) >= date(now())")
    List<CouponHistoryEntity> findByUserAndIsUsed(UserEntity user);
    
}
