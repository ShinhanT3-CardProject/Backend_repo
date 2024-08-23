package com.shinhan.soloplay.raid;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidServiceImpl implements RaidService {

	@Autowired
	RaidRepository raidRepository;
	
	@Override
	public List<RaidDTO> getRaidList() {
	    // 현재 시간을 Timestamp로 생성
	    Timestamp currentTime = new Timestamp(new Date().getTime());

	    // endTime이 현재 시간 이후인 엔티티만 조회
	    List<RaidDTO> raidList = raidRepository
	            .findByEndTimeAfter(currentTime)
	            .stream()
	            .map(entity -> entityToDTO(entity))
	            .collect(Collectors.toList());
	    
		return raidList;
	}
	
	@Override
	public RaidDTO findByRaidId(Long raidId) {
		RaidDTO raidDTO = entityToDTO(raidRepository.findByRaidId(raidId));
		return raidDTO;
	}


}
