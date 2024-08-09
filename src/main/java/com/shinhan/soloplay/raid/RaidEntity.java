package com.shinhan.soloplay.raid;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "raid")
public class RaidEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer raidId;
	
	private int hitPoint;
	private Timestamp startTime;
	private Timestamp endTime;
	private int reward;
	private int isSuccess;
	
}
