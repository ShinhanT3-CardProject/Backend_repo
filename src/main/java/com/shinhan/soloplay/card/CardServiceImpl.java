package com.shinhan.soloplay.card;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {

	@Autowired
	CardRepository cardRepository;

	// 카드목록 전체조회
	@Override
	public List<CardDTO> getList() {
		List<CardDTO> cardlist = cardRepository.findAll().stream().map(en -> entityToDTO(en))
				.collect(Collectors.toList());
		return cardlist;
	}

	// 카드 타입(테마 대분류명)으로 카드들을 조회
	@Override
	public List<CardDTO> getCardsByType(String cardType) {
		List<CardDTO> cardlist = cardRepository.findByCardType(cardType).stream().map(this::entityToDTO)
				.collect(Collectors.toList());
		return cardlist;
	}
	
    // 카드 ID로 카드 이름 조회
    @Override
    public String getCardNameById(Long cardId) {
        Optional<CardEntity> cardEntity = cardRepository.findById(cardId);
        return cardEntity.map(CardEntity::getCardName).orElse(null);
    }

}
