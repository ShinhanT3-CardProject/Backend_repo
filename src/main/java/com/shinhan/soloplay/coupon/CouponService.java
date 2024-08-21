package com.shinhan.soloplay.coupon;

import java.util.List;

public interface CouponService {
	
	void issueCoupon(String userId, Long couponId);
	
    List<CouponHistoryDTO> getCouponHistoryByUser(String userId);
}
