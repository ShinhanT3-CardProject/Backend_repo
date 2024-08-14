package com.shinhan.soloplay.merchant;

import java.util.List;

public interface MerchantService {
	
	List<MerchantDTO> getMerchantById(String merchantID);
	
	default MerchantDTO entityToDTO(MerchantEntity entity) {
		MerchantDTO dto = MerchantDTO.builder()
				.merchantID(entity.getMerchantId())
				.merchantAddress(entity.getMerchantAddress())
				.merchantName(entity.getMerchantName())
				.subCategory(entity.getSubCategory())
				.build();
		return dto;
	}

}
