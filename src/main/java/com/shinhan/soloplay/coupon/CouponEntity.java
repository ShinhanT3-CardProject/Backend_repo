package com.shinhan.soloplay.coupon;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="coupon")
public class CouponEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer couponId;
	private double discountRate;
	private int maxDiscount;
}