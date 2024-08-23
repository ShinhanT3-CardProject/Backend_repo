package com.shinhan.soloplay.point;

import java.util.List;

public interface PointService {
	
	// 사용자 ID에 해당하는 포인트 총합 반환
    int getTotalPointsByUserId(String userId);

    // ID에 해당하는 포인트 리스트 (전체 내역)
    List<PointDTO> getPointsByUserId(String userId);

    // ID에 해당하는 포인트 리스트 (적립 내역만)
    List<PointDTO> getAddedPointsByUserId(String userId);

    // ID에 해당하는 포인트 리스트 (사용 내역만)
    List<PointDTO> getUsedPointsByUserId(String userId);
	
	//새로운 포인트 생성/삭제 및 데이터 베이스 저장
    int createPoint(String userId, PointDTO pointDTO);
	
}
