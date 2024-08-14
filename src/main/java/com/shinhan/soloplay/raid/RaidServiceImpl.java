package com.shinhan.soloplay.raid;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RaidServiceImpl implements RaidService {

	@Autowired
	RaidRepository raidRepository;
	
	@Override
	public List<RaidDTO> getList() {
		List<RaidDTO> raidList = raidRepository
				.findAll()
				.stream()
				.map(entity ->entityToDTO(entity))
				.collect(Collectors.toList());
		return raidList;
	}


}
