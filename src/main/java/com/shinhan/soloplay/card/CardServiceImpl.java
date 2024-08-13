package com.shinhan.soloplay.card;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardRepository cardRepository;
	
	//카드목록 전체조회
	@Override
	public List<CardDTO> getList() { 
		List<CardDTO> cardlist = cardRepository.findAll().stream().map(en->entityToDTO(en))
				.collect(Collectors.toList());
		return cardlist;
	} 

}
