package com.shinhan.soloplay.raid;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping("/raid")
public class RaidController2 {
	
	@Autowired
	RaidService raidService;

	@GetMapping("/raidList")
	public List<RaidDTO> raidList() {
		return raidService.getList();
	}

}
