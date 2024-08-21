package com.shinhan.soloplay.participant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.raid.RaidRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
	
	final RaidRepository raidRepository;
	final ParticipantRepository participantRepository;

	@Override
	public void participate(Long raidId, String userId) {

	}

	@Override
	public List<ParticipantDTO> findByUserId(String userId) {
		List<ParticipantDTO> participantList = participantRepository
				.findByUserId(userId)
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
		System.out.println("UserId : " + userId);
		return participantList;
	}

	@Override
	public ParticipantDTO findById(Long raidId, String userId) {
		// TODO Auto-generated method stub
		return null;
	}

	


}
