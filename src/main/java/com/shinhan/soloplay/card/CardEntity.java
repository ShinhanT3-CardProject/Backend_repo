package com.shinhan.soloplay.card;
import java.sql.Timestamp;
import org.hibernate.annotations.CreationTimestamp;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cardId;
	
	private String cardName;
	private String cardType;
	private String cardBenefit;
	private String cardLink; 
	
}