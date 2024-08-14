package com.shinhan.soloplay.participant;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, ParticipantId>{
	
	@Query("SELECT p FROM ParticipantEntity p WHERE p.participantId.userEntity.id = :userId")
	List<ParticipantEntity> findByUserId(@Param("userId") String userId);
	
	

}
