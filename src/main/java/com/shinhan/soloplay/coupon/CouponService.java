package com.shinhan.soloplay.coupon;

import java.util.List;

public interface CouponService {
	
	//쿠폰 발급
	void issueCoupon(String userId, Long couponId);
	
	//쿠폰ID를 기준으로 발급 내역 조회
    List<CouponWithDetailsDTO> getCouponHistoryByUser(String userId);
    
}
