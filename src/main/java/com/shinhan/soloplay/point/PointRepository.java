package com.shinhan.soloplay.point;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shinhan.soloplay.user.UserEntity;

public interface PointRepository extends JpaRepository<PointEntity, Long> {
	// 특정 사용자에 대한 모든 포인트 엔티티 조회
	List<PointEntity> findByUser(UserEntity user);
	// 특정 사용자에 대한 포인트 적립/사용 내역 조회
	List<PointEntity> findByUserAndIsAdd(UserEntity user, int isAdd);
	// 특정 사용자에 대한 특정 포인트 엔티티 조회
    Optional<PointEntity> findByPointIdAndUser(Long pointId, UserEntity user);
}
