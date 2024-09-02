package com.shinhan.soloplay.user;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
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
public class UserEntity implements Serializable {

    @Id
    @Column(name = "USER_ID", nullable = false)
    private String userId;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;
    
    @Column(name = "USER_PASSWORD", nullable = false)
    private String userPassword;

    @Column(name = "CREATE_DATE", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "IS_ACTIVE", nullable = false)
    private Boolean isActive;

    @PrePersist
    protected void onCreate() {
        if (this.isActive == null) {
            this.isActive = true;
        }
        
    }
    
    
    public static UserEntity fromDTO(UserDTO userDTO) {
        return UserEntity.builder()
                .userId(userDTO.getUserId())
                .userName(userDTO.getUserName())
                .userPassword(userDTO.getUserPassword())
                .createDate(userDTO.getCreateDate() != null 
                            ? userDTO.getCreateDate().toLocalDateTime() 
                            : LocalDateTime.now())  // createDate가 null이면 현재 시각을 사용
                .isActive(userDTO.isActive())
                .build();
    }
    
    public static UserDTO toDTO(UserEntity userEntity) {
        return UserDTO.builder()
                .userId(userEntity.getUserId())
                .userName(userEntity.getUserName())
                .userPassword(userEntity.getUserPassword())
                .createDate(Timestamp.valueOf(userEntity.getCreateDate()))
                .isActive(userEntity.getIsActive())
                .build();
    }
    
    
}
