package com.shinhan.soloplay.coupon;

import java.sql.Timestamp;

import com.shinhan.soloplay.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="coupon_history")
public class CouponHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long couponHistoryId;
	private int isUsed;
	private Timestamp expirationDate;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id",nullable = false)
    private CouponEntity coupon;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="user_id",nullable = false)
	private UserEntity user;
}
