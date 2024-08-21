package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.user.UserEntity;
import com.shinhan.soloplay.user.UserRepository;

@Service
public class CouponServiceImpl implements CouponService{
	
	@Autowired
    CouponRepository couponRepository;

    @Autowired
    CouponHistoryRepository couponHistoryRepository;

    @Autowired
    UserRepository userRepository;

	@Override
	@Transactional
	public void issueCoupon(String userId, Long couponId) {
		// 사용자 및 쿠폰 엔티티 조회
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        CouponEntity coupon = couponRepository.findById(couponId).orElseThrow(() -> new RuntimeException("Coupon not found"));

        // 만료일 설정: 현재로부터 2주 뒤
        LocalDateTime expirationDate = LocalDateTime.now().plusWeeks(2);

        // 쿠폰 히스토리 생성
        CouponHistoryEntity couponHistory = new CouponHistoryEntity();
        couponHistory.setUser(user);
        couponHistory.setCoupon(coupon);
        couponHistory.setIsUsed(1); // 쿠폰 사용 가능 상태
        couponHistory.setExpirationDate(Timestamp.valueOf(expirationDate));

        // 쿠폰 히스토리 저장
        couponHistoryRepository.save(couponHistory);
		
	}

	@Override
    @Transactional(readOnly = true)
    public List<CouponHistoryDTO> getCouponHistoryByUser(String userId) {
        // userId로 UserEntity 조회
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        // 쿠폰 기록 조회 (isUsed가 1인 경우만 조회)
        List<CouponHistoryEntity> couponHistories = couponHistoryRepository.findByUserAndIsUsed(user);
        
        return couponHistories.stream().map(this::convertToDTO).collect(Collectors.toList());
    }
	
	// Entity -> DTO 변환 메서드
    private CouponHistoryDTO convertToDTO(CouponHistoryEntity couponHistory) {
    	return CouponHistoryDTO.builder() 
    			.couponHistoryId(couponHistory.getCouponHistoryId())
                .couponName(couponHistory.getCoupon().getCouponName())
                .isUsed(couponHistory.getIsUsed())
                .expirationDate(couponHistory.getExpirationDate())
                .userId(couponHistory.getUser().getUserId())
                .couponId(couponHistory.getCoupon().getCouponId())
                .build();
    }
}
