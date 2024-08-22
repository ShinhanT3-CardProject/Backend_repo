package com.shinhan.soloplay.point;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserEntity;

@Service
public class PointServiceImpl implements PointService {

    @Autowired
    private PointRepository pointRepository;
    
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
    public void createPoint(String userId, PointDTO pointDTO) {
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
