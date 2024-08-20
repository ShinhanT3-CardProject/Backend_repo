package com.shinhan.soloplay.participant;

import java.util.List;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.raid.RaidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
	
	final RaidRepository raidRepository;

	@Override
	public void participate(Long raidId, String userId) {

	}

	@Override
	public List<ParticipantDTO> findByUserId(String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantDTO findById(Long raidId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
