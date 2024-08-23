package com.shinhan.soloplay.raid;

import java.sql.Timestamp;

import com.shinhan.soloplay.merchant.MerchantEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RaidDTO {

	Long raidId;
	int hitPoint;
	int totalHitPoint;
	Timestamp startTime;
	Timestamp endTime;
	int reward;
	int isSuccess;
	String merchantID;
	String merchantAddress;
	String merchantName;
	
}
