package com.shinhan.soloplay.card;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
@RequestMapping("/card") 
public class CardController {
	
	@Autowired
	CardService cardService;
	
	@GetMapping("/list")
	public List<CardDTO> list(){ 
		return cardService.getList(); 
	}

}
