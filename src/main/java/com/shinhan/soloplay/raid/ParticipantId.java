package com.shinhan.soloplay.raid;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Data
@Embeddable
public class ParticipantId implements Serializable{

	@ManyToOne
	private RaidEntity raidEntity;
	
}