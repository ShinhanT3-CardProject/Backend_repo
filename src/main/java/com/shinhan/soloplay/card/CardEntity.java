package com.shinhan.soloplay.card;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.EmbeddedId;
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
@Table(name = "Card")
public class CardEntity {
	
	@Id
	private int cardId;
	
	private int cardName;
	private int cardType;
	private String cardBenefit;
	private int cardLink; 
	
}