package com.shinhan.soloplay.coupon;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.theme.ThemeService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/coupon")
public class CouponController {
	
	@Autowired
    CouponService couponService;
	
	@Autowired
	ThemeService themeService;
	
	@Autowired
	HttpSession session;

    // 쿠폰 발급 API
    @PostMapping(value = "/issue", consumes = "application/json")
    public ResponseEntity<String> issueCoupon(@RequestBody CouponIssueRequestDTO request) {
        try {
        	String userId = (String) session.getAttribute("loginUser");
        	Long couponId = request.getCouponId();
        	Long themeId = request.getThemeId();
        	String message = couponService.issueCoupon(userId, couponId, themeId);
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // 사용자 Id, 이름 반환
    @GetMapping("/info")
    public ResponseEntity<String> getUserInfo() {
    	// 세션에서 사용자 ID 가져오기
        String userId = (String) session.getAttribute("loginUser");

        if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>(userId, HttpStatus.OK);
    }

    // 사용자의 모든 사용 가능한 쿠폰 기록 조회 API (isUsed가 1인 경우)
    @GetMapping("/usable")
    public ResponseEntity<List<CouponWithDetailsDTO>> getCouponHistoryByUser() {
    	String userId = (String) session.getAttribute("loginUser");
    	if (userId == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    	try {
            List<CouponWithDetailsDTO> couponHistories = couponService.getCouponHistoryByUser(userId);
            return ResponseEntity.ok(couponHistories);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
