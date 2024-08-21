package com.shinhan.soloplay.raid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.card.CardUsageHistoryService;
import com.shinhan.soloplay.participant.ParticipantDTO;
import com.shinhan.soloplay.participant.ParticipantService;

@RestController
@RequestMapping("/raid")
public class RaidController {
	
	@Autowired
	CardUsageHistoryService cardUsageHistoryService;
	
	@Autowired
	ParticipantService participantService;
	
	@GetMapping("/battle/{raidId}/{participantId}")
	public BattleResponseDTO battleDisplay(@PathVariable Long raidId, @PathVariable Long participantId) {
//		String userId = (String)httpSession.getAttribute("loginUser");]
		String userId = "user_1";
		
		List<ParticipantDTO> participants = participantService.findByRaid(participantId, raidId);
		
		int contribution = participantService.userContribution(raidId, userId);
		BattleResponseDTO response = BattleResponseDTO.builder()
				.participants(participants)
				.contribution(contribution)
				.build();
		System.out.println(response);
		return response;
	}

}
