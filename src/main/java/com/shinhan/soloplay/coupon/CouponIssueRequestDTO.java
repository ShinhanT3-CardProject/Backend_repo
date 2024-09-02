package com.shinhan.soloplay.coupon;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponIssueRequestDTO {

	private Long themeId;
	private Long couponId;
}
