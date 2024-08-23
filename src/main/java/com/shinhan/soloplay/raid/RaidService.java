package com.shinhan.soloplay.raid;

import java.util.List;

public interface RaidService {
	
	//진행중인 레이드 목록 조회
	List<RaidDTO> getRaidList();
	
	RaidDTO findByRaidId(Long raidId);
	
	//Entity -> DTO
	//조회용
	default RaidDTO entityToDTO(RaidEntity entity) {
		if(entity == null) return null;
		RaidDTO dto = RaidDTO.builder()
				.raidId(entity.getRaidId())
				.hitPoint(entity.getHitPoint())
				.totalHitPoint(entity.getTotalHitPoint())
				.startTime(entity.getStartTime())
				.endTime(entity.getEndTime())
				.reward(entity.getReward())
				.isSuccess(entity.getIsSuccess())
				.merchantID(entity.getMerchant().getMerchantId())
				.merchantAddress(entity.getMerchant().getMerchantAddress())
				.merchantName(entity.getMerchant().getMerchantName())
				.build();
		return dto;
	}
	
	
}
