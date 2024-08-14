package com.shinhan.soloplay.card;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardUsageHistoryServiceImpl implements CardUsageHistoryService {

	@Autowired
	CardUsageHistoryRepository cardUsageHistoryRepository;
	
	 @Override
	    public List<CardUsageHistoryDTO> getCardUsageHistoryByCardNum(String cardNum) {
	        return cardUsageHistoryRepository.findByUserCard_CardNum(cardNum).stream()
	                .map(this::entityToDTO)
	                .collect(Collectors.toList());
	    }

	    private CardUsageHistoryDTO entityToDTO(CardUsageHistoryEntity entity) {
	        return CardUsageHistoryDTO.builder()
	                .usageId(entity.getUsageId())
	                .transactionDate(entity.getTransaction_date())
	                .amount(entity.getAmount())
	                .cardNum(entity.getUserCard().getCardNum())
	                .build();
	    }

}
