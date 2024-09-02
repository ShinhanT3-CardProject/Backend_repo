package com.shinhan.soloplay.point;

import java.util.List;
import java.util.Map;

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
    
    //스탬프 미션 달성시 랜덤 포인트 지급
    int giveRandomPointReward(String userId, Long themeContentId);
    
    //ID에 해당하는 카테고리별 포인트 비율을 계산
    Map<String,Object> getCategoryData(String userId);
	
}
