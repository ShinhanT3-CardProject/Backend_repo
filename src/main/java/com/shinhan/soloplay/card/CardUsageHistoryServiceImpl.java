package com.shinhan.soloplay.card;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.merchant.MerchantRepository;
import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.raid.RaidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CardUsageHistoryServiceImpl implements CardUsageHistoryService {

	final RaidRepository raidRepository;
	final UserCardRepository userCardRepository;
	final CardUsageHistoryRepository cardUsageHistoryRepository;
	final MerchantRepository merchantRepository;

	@Override
	public List<CardUsageHistoryDTO> getCardUsageHistoryByCardNum(String cardNum) {
		List<CardUsageHistoryEntity> cardUsageHistoryEntityList = cardUsageHistoryRepository
				.findByUserCard_CardNum(cardNum);
		List<CardUsageHistoryDTO> cardUsageHistoryDTOList = cardUsageHistoryEntityList.stream()
				.map(cardUsageHistoryEntity -> addMerchantName(cardUsageHistoryEntity)).collect(Collectors.toList());

		return cardUsageHistoryDTOList;
	}

	// entity를 DTO로 만들 때 merchantName 추가
	private CardUsageHistoryDTO addMerchantName(CardUsageHistoryEntity cardUsageHistoryEntity) {
		String merchantId = cardUsageHistoryEntity.getMerchant().getMerchantId();
		String merchantName = merchantRepository.findMerchantNameById(merchantId);

		CardUsageHistoryDTO cardUsageHistoryDTO = entityToDTO(cardUsageHistoryEntity);
		cardUsageHistoryDTO.setMerchantName(merchantName);

		return cardUsageHistoryDTO;
	}

	// 레이드 결제 내역 전체 조회
	@Override
	public List<CardUsageHistoryDTO> findByRaid(Long raidId, Long usageId) {
		String merchantId = raidRepository.findById(raidId).get().getMerchant().getMerchantId();
		RaidEntity raidEntity = raidRepository.findById(raidId).get();

		List<CardUsageHistoryEntity> cardUsageHistoryEntityList = cardUsageHistoryRepository
				.findByMerchantIdAndUsageId(merchantId, usageId, raidEntity.getStartTime(), raidEntity.getEndTime());
		List<CardUsageHistoryDTO> cardUsageHistoryDTOList = cardUsageHistoryEntityList.stream()
				.map(cardUsageHistoryEntity -> entityToDTO(cardUsageHistoryEntity)).collect(Collectors.toList());
		return cardUsageHistoryDTOList;
	}

}
