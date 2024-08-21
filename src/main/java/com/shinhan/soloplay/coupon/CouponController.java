package com.shinhan.soloplay.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	@Autowired
    CouponService couponService;

    // 쿠폰 발급 API
    @PostMapping(value = "/issue", consumes = "application/json")
    public ResponseEntity<String> issueCoupon(@RequestBody CouponIssueRequestDTO request) {
        try {
        	String userId = request.getUserId();
        	Long couponId = request.getCouponId();
            couponService.issueCoupon(userId, couponId);
            return ResponseEntity.ok("Coupon issued successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // 사용자의 모든 사용 가능한 쿠폰 기록 조회 API (isUsed가1 인 경우)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<CouponHistoryDTO>> getCouponHistoryByUser(@PathVariable String userId) {
        try {
            List<CouponHistoryDTO> couponHistories = couponService.getCouponHistoryByUser(userId);
            return ResponseEntity.ok(couponHistories);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
