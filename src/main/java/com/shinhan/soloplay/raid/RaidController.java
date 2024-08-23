package com.shinhan.soloplay.raid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.card.CardUsageHistoryService;
import com.shinhan.soloplay.participant.ParticipantDTO;
import com.shinhan.soloplay.participant.ParticipantService;
import com.shinhan.soloplay.point.PointDTO;
import com.shinhan.soloplay.point.PointService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/raid")
@RequiredArgsConstructor
public class RaidController {
	
	final RaidService raidService;
	final CardUsageHistoryService cardUsageHistoryService;
	final ParticipantService participantService;
	final PointService pointService;
	
	@GetMapping("/battle/{raidId}")
	public BattleResponseDTO battleDisplay(@PathVariable Long raidId, HttpSession httpSession) {
		String userId = (String)httpSession.getAttribute("loginUser");
		
		List<ParticipantDTO> participants = participantService.findByRaid(raidId);
		RaidDTO raid = raidService.findByRaidId(raidId);
		int contribution = participantService.userContribution(raidId, userId);
		BattleResponseDTO response = BattleResponseDTO.builder()
				.raid(raid)
				.participants(participants)
				.contribution(contribution)
				.build();
		System.out.println(response);
		return response;
	}
	
	@GetMapping("/notification/{raidId}/{participantId}")
	public BattleResponseDTO addtionalParticipant(@PathVariable Long raidId, @PathVariable Long participantId, HttpSession httpSession) {
		String userId = (String)httpSession.getAttribute("loginUser");
		RaidDTO raid = raidService.findByRaidId(raidId);
		int contribution = 0;
		
		List<ParticipantDTO> additionalParticipants = participantService.findAdditionalParticipant(raidId, participantId);
		for (ParticipantDTO participants : additionalParticipants) {
			if (participants.getUserId().equals(userId)) {
				contribution += participants.getContribution();
			}
		}
		
		BattleResponseDTO response = BattleResponseDTO.builder()
				.raid(raid)
				.participants(additionalParticipants)
				.contribution(contribution)
				.build();
		
		return response;
	}
	
	@PostMapping("/reward")
	public ResponseEntity<String> givePoint(@RequestBody RaidRewardRequestDTO request, HttpSession httpSession) {
        try {
        	Long raidId = request.getRaidId();
    		String userId = (String)httpSession.getAttribute("loginUser");
        	int reward = participantService.userReward(raidId, userId);
        	String message = "";
        	
        	if (reward > 0) {
        		message = reward + " 포인트 지급되었습니다.";
            	
            	PointDTO pointDTO = PointDTO.builder()
            			.pointName("레이드 보상")
            			.amount(reward)
            			.isAdd(1)
            			.category(2)
            			.build();
            	
            	pointService.createPoint(userId, pointDTO);
            	
        	} else {
        		message = "포인트 지급 대상이 아닙니다.";
        	}
        	
            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
