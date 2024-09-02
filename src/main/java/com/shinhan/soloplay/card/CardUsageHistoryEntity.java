package com.shinhan.soloplay.card;

import java.sql.Timestamp;

import com.shinhan.soloplay.merchant.MerchantEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CardUsageHistoryEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long usageId;
	
	private Timestamp transactionDate;
	private int amount;
	
	@ManyToOne
    @JoinColumn(name = "merchant_id")
    private MerchantEntity merchant;
	
	@ManyToOne
	@JoinColumn(name = "card_num")
	private UserCardEntity userCard;
}