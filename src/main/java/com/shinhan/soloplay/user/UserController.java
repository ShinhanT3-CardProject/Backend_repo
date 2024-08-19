package com.shinhan.soloplay.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.card.UserCardServiceImpl;


@RestController
public class UserController {

	@Autowired
	UserCardServiceImpl userService;
	
	
	@GetMapping("/card/search/{value}")
	public String findCard(@RequestParam String value) {
		
		
		
		return null; 
	}
	
}
