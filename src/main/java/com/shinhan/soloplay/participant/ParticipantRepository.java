package com.shinhan.soloplay.participant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<ParticipantEntity, ParticipantId>{
	
}
