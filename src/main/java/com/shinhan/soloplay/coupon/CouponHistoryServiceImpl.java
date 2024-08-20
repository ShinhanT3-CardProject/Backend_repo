package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserEntity;

import jakarta.transaction.Transactional;

@Service
public class CouponHistoryServiceImpl implements CouponHistoryService {
	
	@Autowired
	private CouponHistoryRepository couponHistoryRepository;

	
	//쿠폰 만료일 2주 뒤로 설정
	@Override
	@Transactional
	public CouponHistoryDTO createCouponHistory(CouponEntity coupon, UserEntity user) {
        Timestamp expirationDate = Timestamp.from(Instant.now().plusSeconds(14 * 24 * 3600));

        CouponHistoryEntity couponHistory = CouponHistoryEntity.builder()
                .coupon(coupon)
                .user(user)
                .isUsed(1)
                .expirationDate(expirationDate)
                .build();

        CouponHistoryEntity savedCouponHistory = couponHistoryRepository.save(couponHistory);
        return convertToDTO(savedCouponHistory);
    }
	
	// 사용자에 대한 모든 쿠폰 기록 조회
    @Override
    public List<CouponHistoryDTO> getCouponHistoriesByUser(UserEntity user) {
        List<CouponHistoryEntity> couponHistories = couponHistoryRepository.findByUserAndIsUsed(user, 1);
        return couponHistories.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
	
    // 쿠폰 사용으로 마킹
    @Override
    @Transactional
    public CouponHistoryDTO markCouponAsUsed(Long couponHistoryId) {
        Optional<CouponHistoryEntity> optionalCouponHistory = couponHistoryRepository.findById(couponHistoryId);

        if (optionalCouponHistory.isPresent()) {
            CouponHistoryEntity couponHistory = optionalCouponHistory.get();
            couponHistory.setIsUsed(-1);
            CouponHistoryEntity updatedCouponHistory = couponHistoryRepository.save(couponHistory);
            return convertToDTO(updatedCouponHistory);
        } else {
            throw new RuntimeException("CouponHistory with ID " + couponHistoryId + " not found.");
        }
    }
	
	// 엔티티를 DTO로 변환
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
