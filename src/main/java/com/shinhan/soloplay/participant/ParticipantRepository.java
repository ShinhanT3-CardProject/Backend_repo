package com.shinhan.soloplay.participant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, Long>{
	
	List<ParticipantEntity> findByRaidEntityAndUserEntity(RaidEntity raid, UserEntity user);
	List<ParticipantEntity> findByRaidEntityAndUserEntityAndIsRewarded(RaidEntity raid, UserEntity user, int isRewarded);
	List<ParticipantEntity> findByUserEntity(UserEntity user);
	List<ParticipantEntity> findByRaidEntity(RaidEntity raid);
	
	@Query("SELECT p "
			+ "FROM ParticipantEntity p "
			+ "WHERE p.raidEntity.raidId = :raidId "
			+ "AND p.participantId > :participantId")
	List<ParticipantEntity> findAdditionalParticipant(Long raidId, Long participantId);
	
	@Modifying
	@Query("UPDATE ParticipantEntity p SET p.isRewarded = :isRewarded WHERE p.participantId = :participantId")
	int updateIsRewarded(Long participantId, int isRewarded);
}
