package com.shinhan.soloplay.coupon;

import java.util.List;

import com.shinhan.soloplay.user.UserEntity;

public interface CouponHistoryService {
	// 쿠폰 기록 생성, DTO 반환
    CouponHistoryDTO createCouponHistory(CouponEntity coupon, UserEntity user);
    
    // 사용자에 대한 모든 쿠폰 기록 조회, DTO 반환
    List<CouponHistoryDTO> getCouponHistoriesByUser(UserEntity user);
    
    // 쿠폰을 사용으로 마킹, DTO 반환
    CouponHistoryDTO markCouponAsUsed(Long couponHistoryId);
}
