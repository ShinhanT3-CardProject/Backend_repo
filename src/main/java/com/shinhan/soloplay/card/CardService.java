package com.shinhan.soloplay.card;

import java.util.List;

public interface CardService {
	
	//전체 카드목록 조회
	List<CardDTO> getList();
	
	//동적 SQL만들기
	
	//Entity to DTO
	default CardDTO entityToDTO(CardEntity entity) {
		CardDTO dto = CardDTO.builder()
				.cardId(entity.getCardId())
				.cardName(entity.getCardName())
				.cardBenefit(entity.getCardBenefit())
				.cardLink(entity.getCardLink())
				.build();
		return dto;
	}
	
	//DTO to Entity
	default CardEntity dtoToEntity(CardDTO dto) {
		CardEntity entity = CardEntity.builder()
				.cardId(dto.getCardId())
				.cardName(dto.getCardName())
				.cardType(dto.getCardType())
				.cardBenefit(dto.getCardBenefit())
				.cardLink(dto.getCardLink())
				.build();
		return entity;
	}

}
