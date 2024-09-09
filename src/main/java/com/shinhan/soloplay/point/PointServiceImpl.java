package com.shinhan.soloplay.point;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shinhan.soloplay.themecontent.ThemeContentEntity;
import com.shinhan.soloplay.themecontent.ThemeContentRepository;
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
                .mapToInt(pointEntity -> pointEntity.getIsAdd() * pointEntity.getAmount()) 
                .sum();                                                                                                                                                                                                                    
        return Math.max(totalPoints, 0);
    }
    
    // 사용자 ID에 해당하는 카테고리별 포인트 비율을 반환하는 메서드
    @Override
    public Map<String,Object> getCategoryData(String userId){
    	UserEntity userEntity = UserEntity.builder().userId(userId).build();
    	List<PointEntity> pointEntities = pointRepository.findByUser(userEntity);
    	
    	// 차감값은 제외하여 계산
    	int totalPoints = pointEntities.stream()
    			.filter(PointEntity->PointEntity.getIsAdd()>=0)
    			.mapToInt(pointEntity -> pointEntity.getIsAdd() * pointEntity.getAmount()) 
                .sum();
    	
    	// 카테고리별 총 누적 포인트의 비율 계산
    	Map<String, Integer> categoryPoints = pointEntities.stream()
    			.filter(PointEntity->PointEntity.getCategory()>=0)
    			.collect(Collectors.groupingBy(
    					pointEntity -> {
                            // 카테고리 값을 문자열로 변환
                            switch (pointEntity.getCategory()) {
                                case 1: return "테마";
                                case 2: return "레이드";
                                case 0: return "기타";
                                default: return "기타"; 
                            }
                        },
    						Collectors.summingInt(PointEntity-> PointEntity.getIsAdd()* PointEntity.getAmount())
    					));
    	
    	Map<String, Double> categoryRatios = new HashMap<>();
    	
    	if(totalPoints>0) {
    		categoryRatios.put("테마", categoryPoints.getOrDefault("테마", 0)/(double)totalPoints *100);
    		categoryRatios.put("레이드", categoryPoints.getOrDefault("레이드", 0)/(double)totalPoints *100);
    		categoryRatios.put("기타", categoryPoints.getOrDefault("기타", 0)/(double)totalPoints *100);
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
    	// 포인트 차감시 카테고리 -1
    	if (pointDTO.getIsAdd() == -1) {
            pointDTO.setCategory(-1); 
        }
    	
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
    	ThemeContentEntity themeContentEntity = themeContentRepository.findById(themeContentId).get();
    	if (themeContentEntity.getThemeIsSuccess() && (!themeContentEntity.getThemeContentIsRewarded()|| themeContentEntity.getThemeContentIsRewarded()==null) ) {

    		// 1 ~ 500 사이의 랜덤 포인트 생성
            Random random = new Random();
            int randomPoints = random.nextInt(500) + 1;  // 1부터 500까지의 값 생성
            
            // PointDTO 생성
            PointDTO pointDTO = PointDTO.builder()
                .pointName("랜덤 포인트 지급")
                .amount(randomPoints)
                .isAdd(1) // 1은 테마 적립
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
