package com.shinhan.soloplay.participant;

import java.util.List;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;


public interface ParticipantService {
	

	List<ParticipantDTO> findByUserId(String userId);
	List<ParticipantDTO> findByRaid(Long participantId, Long raidId);
	int userContribution(Long raidId, String userId);
	
	//Entity -> DTO
	default ParticipantDTO entityToDTO(ParticipantEntity participantEntity) {
		ParticipantDTO participantDTO = ParticipantDTO.builder()
				.participantId(participantEntity.getParticipantId())
				.contribution(participantEntity.getContribution())
				.raidId(participantEntity.getRaidEntity().getRaidId())
				.userId(participantEntity.getUserEntity().getUserId())
				.build();
		return participantDTO;
	}
	
	//DTO -> Entity
	default ParticipantEntity dtoToEntity(ParticipantDTO participantDTO) {
		UserEntity userEntity = UserEntity.builder()
				.userId(participantDTO.getUserId())
				.build();
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(participantDTO.getRaidId())
				.build();
		ParticipantEntity participantEntity = ParticipantEntity.builder()
				.participantId(participantDTO.getParticipantId())
				.contribution(participantDTO.getContribution())
				.userEntity(userEntity)
				.raidEntity(raidEntity)
				.build();
		return participantEntity;
	}
}
