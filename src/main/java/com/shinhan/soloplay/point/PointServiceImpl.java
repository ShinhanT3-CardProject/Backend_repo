package com.shinhan.soloplay.point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.theme.ThemeContentRepository;
import com.shinhan.soloplay.user.UserEntity;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;
    
    @Autowired
    ThemeContentRepository themeContentRepository;
    
    // 사용자 ID에 해당하는 포인트 총합을 반환하는 메서드
    @Override
    public int getTotalPointsByUserId(String userId) {
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
        List<PointEntity> pointEntities = pointRepository.findByUser(userEntity);
        int totalPoints = pointEntities.stream()
                .mapToInt(pointEntity -> pointEntity.getIsAdd() * pointEntity.getAmount()) // Adjust total by isAdd value
                .sum();                                                                                                                                                                                                                    
        return Math.max(totalPoints, 0);
    }
    
    // 사용자 ID에 해당하는 카테고리별 포인트 비율을 반환하는 메서드
    @Override
    public Map<String,Object> getCategoryData(String userId){
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
    	List<PointEntity> pointEntities = pointRepository.findByUser(userEntity);
    	
    	int totalPoints = getTotalPointsByUserId(userId);
    	
    	//카테고리별 총 포인트 계산
    	Map<Integer, Integer> categoryPoints = pointEntities.stream()
    			.collect(Collectors.groupingBy(
    						PointEntity::getCategory,
    						Collectors.summingInt(PointEntity-> PointEntity.getIsAdd()* PointEntity.getAmount())
    					));
    	
    	Map<String, Double> categoryRatios = new HashMap<>();
    	
    	if(totalPoints>0) {
    		categoryRatios.put("테마", categoryPoints.getOrDefault(1, 0)/(double)totalPoints *100);
    		categoryRatios.put("레이드", categoryPoints.getOrDefault(2, 0)/(double)totalPoints *100);
    		categoryRatios.put("기타", categoryPoints.getOrDefault(0, 0)/(double)totalPoints *100);
    	}else {
    		categoryRatios.put("테마", 0.0);
            categoryRatios.put("레이드", 0.0);
            categoryRatios.put("기타", 0.0);
    	}
    	
    	Map<String,Object> result = new HashMap<>();
    	result.put("categoryTotals", categoryPoints);
    	result.put("categoryRatios", categoryRatios);
    	
    	
    	return result;
    }
    
    // 사용자 ID에 해당하는 모든 포인트 내역을 반환하는 메서드
    @Override
    public List<PointDTO> getPointsByUserId(String userId) {
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
        List<PointEntity> pointEntities = pointRepository.findByUser(userEntity);
        return pointEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 사용자 ID에 해당하는 적립 포인트 내역만 반환하는 메서드
    @Override
    public List<PointDTO> getAddedPointsByUserId(String userId) {
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
        List<PointEntity> pointEntities = pointRepository.findByUserAndIsAdd(userEntity, 1);
        return pointEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 사용자 ID에 해당하는 사용 포인트 내역만 반환하는 메서드
    @Override
    public List<PointDTO> getUsedPointsByUserId(String userId) {
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
        List<PointEntity> pointEntities = pointRepository.findByUserAndIsAdd(userEntity, -1);
        return pointEntities.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    // 사용자 ID와 함께 새로운 포인트를 생성하고 반환하는 메서드
    @Override
    public int createPoint(String userId, PointDTO pointDTO) {
    	int maxPoint = getTotalPointsByUserId(userId);
    	
    	if (pointDTO.getIsAdd() == -1 && pointDTO.getAmount() > maxPoint) return 0; // 실패
    	
        PointEntity pointEntity = PointEntity.builder()
                .pointId(pointDTO.getPointId())
                .pointName(pointDTO.getPointName())
                .amount(pointDTO.getAmount())
                .createDate(pointDTO.getCreateDate())
                .isAdd(pointDTO.getIsAdd())
                .category(pointDTO.getCategory())
                .user(UserEntity.builder().userId(userId).build()) 
                .build();
        
        pointRepository.save(pointEntity);
        return pointDTO.getAmount(); // 성공
    }
    
    //스탬프 미션 달성시 랜덤 포인트 지급(1~500P)
    @Transactional
    public int giveRandomPointReward(String userId, Long themeContentId) {
    	if (!themeContentRepository.findById(themeContentId).get().getThemeContentIsRewarded()) {
    		System.out.println(!themeContentRepository.findById(themeContentId).get().getThemeContentIsRewarded());
    		// 1 ~ 500 사이의 랜덤 포인트 생성
            Random random = new Random();
            int randomPoints = random.nextInt(500) + 1;  // 1부터 500까지의 값 생성
            
            // PointDTO 생성
            PointDTO pointDTO = PointDTO.builder()
                .pointName("랜덤 포인트 지급")
                .amount(randomPoints)
                .isAdd(1) // 1은 포인트 추가를 의미
                .category(1)
                .build();

            createPoint(userId, pointDTO); // 포인트 적립 메서드 호출
            themeContentRepository.updateIsRewarded(themeContentId);
            System.out.println(themeContentId);
            // 포인트 적립
            return randomPoints;
    	}else {
    		return 0;
    	}
    	
    }

    private PointDTO convertToDTO(PointEntity pointEntity) {
        return PointDTO.builder()
                .pointId(pointEntity.getPointId())
                .pointName(pointEntity.getPointName())
                .amount(pointEntity.getAmount())
                .createDate(pointEntity.getCreateDate())
                .isAdd(pointEntity.getIsAdd())
                .category(pointEntity.getCategory())
                .build();
    }
}
