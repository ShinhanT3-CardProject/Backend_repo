package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserEntity;

import jakarta.transaction.Transactional;

@Service
public class CouponHistoryServiceImpl implements CouponHistoryService {
	
	@Autowired
	private CouponHistoryRepository couponHistoryRepository;
	
	@Autowired
	private CouponRepository couponRepository;

	@Override
	@Transactional
	public CouponHistoryEntity createCouponHistory(CouponEntity coupon, UserEntity user) {
		Timestamp expirationDate = Timestamp.from(Instant.now().plusSeconds(14 * 24 * 3600));

        CouponHistoryEntity couponHistory = CouponHistoryEntity.builder()
                .coupon(coupon)
                .user(user)
                .isUsed(0)
                .expirationDate(expirationDate)
                .build();

        return couponHistoryRepository.save(couponHistory);
	}

	@Override
	public List<CouponHistoryEntity> getCouponHistoriesByUser(UserEntity user) {
        return couponHistoryRepository.findByUserAndIsUsed(user, 0);
    }

	@Override
	@Transactional
    public CouponHistoryEntity markCouponAsUsed(Long couponHistoryId) {
        Optional<CouponHistoryEntity> optionalCouponHistory = couponHistoryRepository.findById(couponHistoryId);

        if (optionalCouponHistory.isPresent()) {
            CouponHistoryEntity couponHistory = optionalCouponHistory.get();
            couponHistory.setIsUsed(1);
            return couponHistoryRepository.save(couponHistory);
        } else {
            throw new RuntimeException("CouponHistory with ID " + couponHistoryId + " not found.");
        }
    }
	
}
