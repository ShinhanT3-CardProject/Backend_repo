package com.shinhan.soloplay.coupon;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.user.UserEntity;
import com.shinhan.soloplay.user.UserRepository;

@RestController
@RequestMapping("/coupon")
public class CouponHistoryController {
	@Autowired
    private CouponHistoryService couponHistoryService;

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private CouponRepository couponRepository;
    
    // 쿠폰 기록 생성(== 쿠폰 발급)
    @PostMapping("/create")
    public ResponseEntity<CouponHistoryDTO> createCouponHistory(@RequestParam Long couponId, @RequestParam String userId) {
        Optional<CouponEntity> couponOpt = couponRepository.findById(couponId);
        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (couponOpt.isPresent() && userOpt.isPresent()) {
            CouponHistoryDTO couponHistory = couponHistoryService.createCouponHistory(couponOpt.get(), userOpt.get());
            return new ResponseEntity<>(couponHistory, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 사용자에 대한 모든 쿠폰 기록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponHistoryDTO>> getCouponHistoriesByUser(@PathVariable String userId) {
        Optional<UserEntity> userOpt = userRepository.findById(userId);

        if (userOpt.isPresent()) {
            List<CouponHistoryDTO> couponHistories = couponHistoryService.getCouponHistoriesByUser(userOpt.get());
            return new ResponseEntity<>(couponHistories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // 쿠폰을 사용으로 마킹
    @PutMapping("/use/{couponHistoryId}")
    public ResponseEntity<CouponHistoryDTO> markCouponAsUsed(@PathVariable Long couponHistoryId) {
        try {
            CouponHistoryDTO updatedCouponHistory = couponHistoryService.markCouponAsUsed(couponHistoryId);
            return new ResponseEntity<>(updatedCouponHistory, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
