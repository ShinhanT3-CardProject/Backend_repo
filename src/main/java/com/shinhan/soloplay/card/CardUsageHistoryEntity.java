package com.shinhan.soloplay.card;
import java.math.BigDecimal;
import java.time.LocalDateTime;

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
	
	private LocalDateTime transaction_date;
	private BigDecimal cardType;
	
	@ManyToOne
    @JoinColumn(name = "merchant_id")
    private MerchantEntity merchant;
	
}