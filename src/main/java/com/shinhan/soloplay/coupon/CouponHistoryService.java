package com.shinhan.soloplay.coupon;

import java.util.List;

import com.shinhan.soloplay.user.UserEntity;

public interface CouponHistoryService {
	//user가 특정 쿠폰을 사용할 수 있도록 기록을 생성하며, 만료일을 현재 시점으로부터 2주 후로 설정
	CouponHistoryEntity createCouponHistory(CouponEntity coupon, UserEntity user);
	//user가 보유한 모든 사용 가능한 쿠폰 목록을 조회
    List<CouponHistoryEntity> getCouponHistoriesByUser(UserEntity user);
    //user가 사용한 쿠폰 '사용됨' 업데이트
    CouponHistoryEntity markCouponAsUsed(Long couponHistoryId);
}
