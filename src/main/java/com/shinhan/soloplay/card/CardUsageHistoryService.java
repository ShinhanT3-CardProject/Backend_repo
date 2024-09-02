package com.shinhan.soloplay.card;

import java.util.List;

import com.shinhan.soloplay.merchant.MerchantEntity;

public interface CardUsageHistoryService {

	List<CardUsageHistoryDTO> getCardUsageHistoryByCardNum(String cardNum);

	// 레이드 결제 내역 전체 조회
	List<CardUsageHistoryDTO> findByRaid(Long raidId, Long usageId);

	// Entity -> DTO
	default CardUsageHistoryDTO entityToDTO(CardUsageHistoryEntity cardUsageHistoryEntity) {
		CardUsageHistoryDTO cardUsageHistoryDTO = CardUsageHistoryDTO.builder()
				.usageId(cardUsageHistoryEntity.getUsageId())
				.transactionDate(cardUsageHistoryEntity.getTransactionDate())
				.amount(cardUsageHistoryEntity.getAmount())
				.cardNum(cardUsageHistoryEntity.getUserCard().getCardNum())
				.merchantName(cardUsageHistoryEntity.getMerchant().getMerchantName())
				.build();
		return cardUsageHistoryDTO;
	}

	// DTO -> Entity
	default CardUsageHistoryEntity dtoToEntity(CardUsageHistoryDTO cardUsageHistoryDTO) {
		UserCardEntity userCardEntity = UserCardEntity.builder()
				.cardNum(cardUsageHistoryDTO.getCardNum())
				.build();
		MerchantEntity merchantEntity = MerchantEntity.builder()
				.merchantId(cardUsageHistoryDTO.getMerchantId())
				.merchantName(cardUsageHistoryDTO.getMerchantName())
				.build();

		CardUsageHistoryEntity cardUsageHistoryEntity = CardUsageHistoryEntity.builder()
				.usageId(cardUsageHistoryDTO.getUsageId())
				.transactionDate(cardUsageHistoryDTO.getTransactionDate())
				.amount(cardUsageHistoryDTO.getAmount())
				.merchant(merchantEntity)
				.userCard(userCardEntity)
				.build();
		return cardUsageHistoryEntity;
	}
}
