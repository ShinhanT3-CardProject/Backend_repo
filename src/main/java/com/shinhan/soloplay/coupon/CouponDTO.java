package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDTO {
	Long couponId;
	String couponName;
	double discountRate;
	int maxDiscount;
}
