package com.shinhan.soloplay.participant;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.raid.RaidRepository;
import com.shinhan.soloplay.theme.ThemeService;
import com.shinhan.soloplay.user.UserEntity;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {
	
	final ParticipantRepository participantRepository;
	final RaidRepository raidRepository;
	final ThemeService themeService;

	@Override
	public List<ParticipantDTO> findByRaid(Long raidId) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		List<ParticipantDTO> participantDTOList = participantRepository.findByRaidEntity(raidEntity)
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
		return participantDTOList;
	}

	@Override
	public List<ParticipantDTO> findAdditionalParticipant(Long raidId, Long participantId) {
		List<ParticipantDTO> participantDTOList = participantRepository.findAdditionalParticipant(raidId, participantId)
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
		return participantDTOList;
	}

	
	@Override
	public int userContribution(Long raidId, String userId) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		List<Integer> contributionList = participantRepository.findByRaidEntityAndUserEntity(raidEntity, userEntity)
				.stream()
				.map(ParticipantEntity::getContribution)
				.collect(Collectors.toList());
		
		int result = 0;
		
		for (int i : contributionList) {
			result += i;
		}
		
		return result;
	}


	@Override
	public List<ParticipantDTO> findByUserId(String userId) {
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		List<ParticipantDTO> participantDTOList = participantRepository.findByUserEntity(userEntity)
				.stream()
				.map(entity -> entityToDTO(entity))
				.collect(Collectors.toList());
		return participantDTOList;
	}


	@Override
	@Transactional
	public int userReward(Long raidId, String userId) {
		RaidEntity raidEntity = raidRepository.findById(raidId).get();
		if (raidEntity.getHitPoint() == 0) {
			int totalHitPoint = raidEntity.getTotalHitPoint();
			int reward = raidEntity.getReward();
			int contribution = userRewardNotGiven(raidId, userId);
			int buff = 1;
			if (themeService.getIsSuccess(userId) == 5) buff = 2;
			
			double result = reward*contribution/totalHitPoint*buff;
			return (int)result;
		}else {
			return 0;
		}
	}

	private int userRewardNotGiven(Long raidId, String userId) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		List<ParticipantEntity> participantList = participantRepository.findByRaidEntityAndUserEntityAndIsRewarded(raidEntity, userEntity, 1);
		List<Integer> contributionList = participantList
				.stream()
				.map(ParticipantEntity::getContribution)
				.collect(Collectors.toList());
		
		int result = 0;
		
		for (int i : contributionList) {
			result += i;
		}
		
		participantList.stream()
			.forEach(entity -> {
				int updatedRows = participantRepository.updateIsRewarded(entity.getParticipantId(), 2);
				System.out.println("Updated Rows: " + updatedRows);
			});
		
		return result;
	}

}
