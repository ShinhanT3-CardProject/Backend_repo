package com.shinhan.soloplay.raid;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidRepository extends JpaRepository<RaidEntity, Long>{
	List<RaidEntity> findByEndTimeAfter(Timestamp currentTime);
	RaidEntity findByRaidId(Long raidId);

}
