package com.shinhan.soloplay.participant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParticipantDTO {
	
	Long participantId;	
	int contribution;
	int isRewarded;
	Long raidId;
	String userId;


}
