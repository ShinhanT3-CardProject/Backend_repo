package com.shinhan.soloplay.raid;

import java.sql.Timestamp;

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
	Timestamp startTime;
	Timestamp endTime;
	int reward;
	int isSuccess;
}
