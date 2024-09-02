package com.shinhan.soloplay.raid;

import java.util.List;

import com.shinhan.soloplay.participant.ParticipantDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BattleResponseDTO {
	private RaidDTO raid;
	private List<ParticipantDTO> participants;
	private int contribution;
	private int buff;
}
