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
public class CouponHistoryDTO {
	Long couponHistoryId;
	String couponName;
	int isUsed;
	Timestamp expirationDate;
	String userId;
	Long couponId;
}
