package com.shinhan.soloplay.card;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardDTO {
	private Long cardId;
	private String cardName;
	private String cardType;
	private String cardBenefit;
	private String cardLink;

}
