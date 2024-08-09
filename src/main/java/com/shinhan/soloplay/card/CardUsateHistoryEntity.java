package com.shinhan.soloplay.card;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Card_Usage_History")
public class CardUsateHistoryEntity {
	
	@Id
	private int usageId;
	
	private LocalDateTime cardName;
	private int cardType;
	private String cardBenefit;
	private int cardLink; 
	
}