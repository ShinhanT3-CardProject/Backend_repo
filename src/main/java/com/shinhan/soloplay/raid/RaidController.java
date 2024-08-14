package com.shinhan.soloplay.raid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.participant.ParticipantDTO;
import com.shinhan.soloplay.participant.ParticipantService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/raid")
public class RaidController {
	
	@Autowired
	ParticipantService participantService;

	//레이드 참가
	@GetMapping("/participate/{raidId}")
	void participate(@PathVariable("raidId") Long raidId, HttpSession httpSession) {
		String userId = (String)httpSession.getAttribute("loginUser");
		
		if (participantService.findById(raidId, userId) == null) {
			participantService.participate(raidId, userId);
		}else {
			participantService.addAttack(raidId, userId);
		}
	}
	
	//레이드 결과 조회
	@GetMapping("/result/{raidId}")
	ParticipantDTO result(@PathVariable("raidId") Long raidId, HttpSession httpSession) {
		String userId = (String)httpSession.getAttribute("loginUser");
		return participantService.findById(raidId, userId);
	}
}
