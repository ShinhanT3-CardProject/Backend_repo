package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.theme.ThemeRepository;
import com.shinhan.soloplay.themecontent.ThemeContentRepository;
import com.shinhan.soloplay.user.UserEntity;
import com.shinhan.soloplay.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

	final CouponRepository couponRepository;
	final CouponHistoryRepository couponHistoryRepository;
	final UserRepository userRepository;
	final ThemeRepository themeRepository;
	final ThemeContentRepository themeContentRepository;

	@Override
	@Transactional
	public String issueCoupon(String userId, Long couponId, Long themeId) {
		
		if (!themeRepository.findById(themeId).get().getThemeIsRewarded()
				&& themeContentRepository.countAllByThemeIsSuccessTrue(themeId) == 5) {
			
			// 사용자 및 쿠폰 엔티티 조회
			UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
			CouponEntity coupon = couponRepository.findById(couponId)
					.orElseThrow(() -> new RuntimeException("Coupon not found"));

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
            themeRepository.updateThemeIsRewarded(themeId);
            
            return "쿠폰 발급이 완료되었습니다.";
    	}
		
		return "쿠폰 발급 대상이 아닙니다.";

	}

	@Override
	@Transactional(readOnly = true)
	public List<CouponWithDetailsDTO> getCouponHistoryByUser(String userId) {
		// userId로 UserEntity 조회
		UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

		// 쿠폰 기록 조회 및 저장(isUsed가 1인 경우만 조회) 
		List<CouponHistoryEntity> couponHistories = couponHistoryRepository.findByUserAndIsUsed(user);

		// 쿠폰 ID를 기반으로 쿠폰 정보 조회
		List<Long> couponIds = couponHistories.stream().map(ch -> ch.getCoupon().getCouponId())
				.collect(Collectors.toList());

		List<CouponEntity> coupons = couponRepository.findByCouponIdIn(couponIds);

		// 쿠폰 정보와 쿠폰 히스토리를 결합하여 DTO 생성
		return couponHistories.stream().map(ch -> convertToDTO(ch, coupons)).collect(Collectors.toList());
	}

	// 쿠폰 히스토리와 쿠폰 정보를 결합하여 DTO 생성 -> 쿠폰 사용 내역과 해당 쿠폰의 세부 정보를 한 번에 제공
	private CouponWithDetailsDTO convertToDTO(CouponHistoryEntity couponHistory, List<CouponEntity> coupons) {
		CouponEntity coupon = coupons.stream()
				.filter(c -> c.getCouponId().equals(couponHistory.getCoupon().getCouponId())).findFirst().orElse(null);

		return CouponWithDetailsDTO.builder().couponHistoryId(couponHistory.getCouponHistoryId())
				.couponName(coupon != null ? coupon.getCouponName() : null)
				.discountRate(coupon != null ? coupon.getDiscountRate() : 0)
				.maxDiscount(coupon != null ? coupon.getMaxDiscount() : 0)
				.expirationDate(couponHistory.getExpirationDate()).userId(couponHistory.getUser().getUserId())
				.couponId(couponHistory.getCoupon().getCouponId()).build();
	}
}
