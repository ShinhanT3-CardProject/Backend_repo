package com.shinhan.soloplay.participant;

import java.util.List;
import java.util.Map;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;


public interface ParticipantService {
	
	//CRUD
	//1.Create
	void participate(Long raidId, String userId, int attack);
	
	//2.Read
	List<ParticipantDTO> findByUserId(String userId);
	ParticipantDTO findById(Long raidId, String userId);
	
	//3.Update
	void addAttack(Long raidId, String userId, int attackIncrement);
	Map<String, Integer> attack(ParticipantDTO dto);
	
	//Entity -> DTO
	default ParticipantDTO entityToDTO(ParticipantEntity participantEntity) {
		ParticipantDTO participantDTO = ParticipantDTO.builder()
				.raidId(participantEntity.getParticipantId().getRaidEntity().getRaidId())
				.userId(participantEntity.getParticipantId().getUserEntity().getUserId())
				.contribution(participantEntity.getContribution())
				.attack(participantEntity.getAttack())
				.createTime(participantEntity.getCreateTime())
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
		ParticipantId participantId = ParticipantId.builder()
				.userEntity(userEntity)
				.raidEntity(raidEntity)
				.build();
		
		ParticipantEntity participantEntity = ParticipantEntity.builder()
				.participantId(participantId)
				.attack(participantDTO.getAttack())
				.contribution(participantDTO.getContribution())
				.createTime(participantDTO.getCreateTime())
				.build();
		return participantEntity;
	}
}
