package com.shinhan.soloplay.point;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
@Table(name="point")
public class PointEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer pointId;
	private String pointName;
	private int amount;
	private LocalDateTime createDate;
	private int isAdd;
	private LocalDateTime deadDate;
	private int category;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="user_id", nullable = false)
	private UserEntity user;
	
}
