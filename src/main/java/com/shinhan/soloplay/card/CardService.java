package com.shinhan.soloplay.card;

import java.util.List;

public interface CardService {

	// 전체 카드목록 조회
	List<CardDTO> getList();

	// 카드 타입(테마 대분류명)으로 카드들을 조회
	List<CardDTO> getCardsByType(String cardType);  
	
	 // 카드 ID로 카드 이름 조회
    String getCardNameById(Long cardId);
	
	// Entity to DTO
	default CardDTO entityToDTO(CardEntity entity) {
		CardDTO dto = CardDTO.builder().cardId(entity.getCardId()).cardName(entity.getCardName())
				.cardBenefit(entity.getCardBenefit()).cardLink(entity.getCardLink()).build();
		return dto;
	}

	// DTO to Entity
	default CardEntity dtoToEntity(CardDTO dto) {
		CardEntity entity = CardEntity.builder().cardId(dto.getCardId()).cardName(dto.getCardName())
				.cardType(dto.getCardType()).cardBenefit(dto.getCardBenefit()).cardLink(dto.getCardLink()).build();
		return entity;
	}

}
