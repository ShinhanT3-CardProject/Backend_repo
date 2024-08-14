package com.shinhan.soloplay.participant;
import java.sql.Timestamp;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
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
	
	@EmbeddedId
	private ParticipantId participantId;
	
	private int contribution;
	private int attack;
	private Timestamp createTime;

}