package com.shinhan.soloplay.raid;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.card.CardUsageHistoryDTO;
import com.shinhan.soloplay.card.CardUsageHistoryService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/raid")
public class RaidController {
	
	@Autowired
	CardUsageHistoryService cardUsageHistoryService;
	
	@GetMapping("/battle")
	public Map<String, Object> battleDisplay(@RequestBody Map<String, Long> requestBody, HttpSession httpSession) {
		String userId = (String)httpSession.getAttribute("loginUser");
		Long raidId = requestBody.get("raidId");
		Long usageId = requestBody.getOrDefault("usageId", 0L);
		int amount = 0;
		
		List<CardUsageHistoryDTO> cardUsageHistoryList = cardUsageHistoryService.findByRaid(raidId, usageId);
		
		Map<String, Object> responseBody = new HashMap<>();
		responseBody.put("raidHistory", cardUsageHistoryList);
		responseBody.put("userAmount", amount);
		
		return responseBody;
	}

}
