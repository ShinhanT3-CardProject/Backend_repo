package com.shinhan.soloplay.participant;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

	final ParticipantRepository pRepo;

	@Override
	public void create(ParticipantDTO dto) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ParticipantDTO> readAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ParticipantDTO readById(Long raidId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(ParticipantDTO dto) {
		// TODO Auto-generated method stub
		
	}
	
	
}
