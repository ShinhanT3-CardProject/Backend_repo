package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponWithDetailsDTO {
	private Long couponHistoryId;
    private String couponName;
    private double discountRate;
    private int maxDiscount;
    private Timestamp expirationDate;
    private String userId;
    private Long couponId;
}
