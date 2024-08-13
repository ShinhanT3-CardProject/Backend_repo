package com.shinhan.soloplay.participant;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDTO {
	
	Long raidId;
	String userId;
	int contribution;
	int attack;
	Timestamp createTime;
}
