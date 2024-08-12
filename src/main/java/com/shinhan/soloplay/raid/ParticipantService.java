package com.shinhan.soloplay.raid;

import java.util.List;

public interface ParticipantService {

	//CRUD
	//1.Create
	void create(ParticipantDTO dto);
	
	//2.Read
	List<ParticipantDTO> readAll();
	ParticipantDTO readById(Long raidId, String userId);
	
	//3.Update
	void update(ParticipantDTO dto);
	
	//Entity -> DTO
	default ParticipantDTO entityToDTO(ParticipantEntity entity) {
		ParticipantDTO dto = ParticipantDTO.builder()
				.raidId(entity.getParticipantId().getRaidEntity().getRaidId())
				.userId(entity.getParticipantId().getUserEntity().getUserId())
				.contribution(entity.getContribution())
				.attack(entity.getAttack())
				.createTime(entity.getCreateTime())
				.build();
		return dto;
	}
	
	//DTO -> Entity
	// raidEntity와 userEntity 만든 후 만들 수 있음
//	default ParticipantEntity dtoToEntity(ParticipantDTO dto) {
//		ParticipantId participantId;
//		ParticipantEntity entity = ParticipantEntity.builder()
//				.participantId(participantId)
//				.build();
//		return entity;
//	}
}
