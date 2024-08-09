package com.shinhan.soloplay.auth;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "USER")
public class AuthEntity {
	@Id
	@Column(name = "USER_ID", nullable = true, length = 255)
	private String userId;

	@Column(name = "USER_NAME", nullable = false, length = 255)
	private String userName;

	@Column(name = "USER_PWD", nullable = false, length = 255)
	private String userPwd;

	@Column(name = "CREATE_DATE", nullable = false)
	private LocalDateTime createDate;

	@Column(name = "IS_ACTIVE", nullable = true)
	private Boolean isActive;
}
