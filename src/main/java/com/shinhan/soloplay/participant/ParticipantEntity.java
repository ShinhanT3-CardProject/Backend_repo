package com.shinhan.soloplay.participant;

import org.hibernate.annotations.ColumnDefault;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "participant")
public class ParticipantEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long participantId;
	
	@ColumnDefault("0")
	private int contribution;
	
	@ColumnDefault("0")
	private int isRewarded;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
	private UserEntity userEntity;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RAID_ID")
	private RaidEntity raidEntity;

}