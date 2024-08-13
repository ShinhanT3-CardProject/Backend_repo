package com.shinhan.soloplay.participant;

import java.io.Serializable;

import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;

import jakarta.persistence.Column;
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

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private RaidEntity raidEntity;
	
	@ManyToOne
	private UserEntity userEntity;
}
