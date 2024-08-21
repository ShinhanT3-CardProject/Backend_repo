package com.shinhan.soloplay.raid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.merchant.MerchantDTO;
import com.shinhan.soloplay.merchant.MerchantService;
import com.shinhan.soloplay.participant.ParticipantDTO;
import com.shinhan.soloplay.participant.ParticipantService;

@CrossOrigin
@RestController
@RequestMapping("/raid")
public class RaidController2 {
	
	@Autowired
	RaidService raidService;

	@Autowired
	ParticipantService participantService;
	
	@GetMapping("/raidList")
	public List<RaidDTO> raidList() {
		return raidService.getList();
	}
	
	@GetMapping("/raidHistory")
	public List<ParticipantDTO> raidHistory(String userId) {
		return participantService.findByUserId(userId);
	}
	
	@GetMapping("/raidHistory/detail")
	public RaidDTO raidDetail(Long raidId) {
		return raidService.findByRaidId(raidId);
	}

}
