package com.shinhan.soloplay.user;
import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	private String userId;
	private String userPassword;
	private String userName;
	private Timestamp createDate;
	private boolean isActive;
	
	
}
