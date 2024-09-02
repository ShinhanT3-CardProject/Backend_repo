package com.shinhan.soloplay.coupon;

import java.util.List;

public interface CouponService {
	
	//쿠폰 발급
	String issueCoupon(String userId, Long couponId, Long themeId);
	
	//쿠폰ID를 기준으로 발급 내역 조회
    List<CouponWithDetailsDTO> getCouponHistoryByUser(String userId);
    
}
