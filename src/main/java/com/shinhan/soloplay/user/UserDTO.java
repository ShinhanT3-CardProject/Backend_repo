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
	
	public static UserEntity toEntity(UserDTO userDTO) {
	    return UserEntity.builder()
	            .userId(userDTO.getUserId())
	            .userName(userDTO.getUserName())
	            .userPassword(userDTO.getUserPassword())
	            .createDate(userDTO.getCreateDate().toLocalDateTime())
	            .isActive(userDTO.isActive())
	            .build();
	}
	
}
