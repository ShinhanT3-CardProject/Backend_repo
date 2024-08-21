package com.shinhan.soloplay.raid;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RaidRepository extends JpaRepository<RaidEntity, Long>{
	RaidEntity findByRaidId(Long raidId);

}
