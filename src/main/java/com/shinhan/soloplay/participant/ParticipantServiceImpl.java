package com.shinhan.soloplay.participant;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.shinhan.soloplay.card.CardUsageHistoryRepository;
import com.shinhan.soloplay.raid.RaidEntity;
import com.shinhan.soloplay.user.UserEntity;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParticipantServiceImpl implements ParticipantService {

	final ParticipantRepository participantRepository;
//	final RaidRepository raidRepository;
	final CardUsageHistoryRepository cardUsageHistoryRepository;

	//레이드 참가(Create)
	@Override
	public void participate(Long raidId, String userId) {
		int attack = calculateAttack(userId, raidId);
		ParticipantDTO participantDTO = ParticipantDTO.builder()
				.raidId(raidId)
				.userId(userId)
				.attack(attack)
				.build();
		ParticipantEntity participantEntity = dtoToEntity(participantDTO); 
		participantRepository.save(participantEntity);
	}
	
	//유저의 레이드 내역 조회(Read)
	@Override
	public List<ParticipantDTO> findByUserId(String userId) {
		List<ParticipantEntity> participantEntityList = participantRepository.findByUserId(userId);
		List<ParticipantDTO> participantDTOList = 
				participantEntityList.stream()
					.map(participantEntity -> entityToDTO(participantEntity))
					.collect(Collectors.toList());				
		return participantDTOList;
	}

	//레이드 결과 조회(Read)
	@Override
	public ParticipantDTO findById(Long raidId, String userId) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		ParticipantId participantId = ParticipantId.builder()
				.raidEntity(raidEntity)
				.userEntity(userEntity)
				.build();
		ParticipantEntity participantEntity = participantRepository.findById(participantId)
				.orElse(null);
		return entityToDTO(participantEntity);
	}
	
	//레이드 추가 공격(Update)
	@Override
	public void addAttack(Long raidId, String userId) {
		int attackIncrement = calculateAttack(userId, raidId);
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		ParticipantId participantId = ParticipantId.builder()
				.raidEntity(raidEntity)
				.userEntity(userEntity)
				.build();
		participantRepository.findById(participantId)
			.ifPresent(participant->{
				participant.setAttack(participant.getAttack() + attackIncrement);
				participant.setCreateTime(new Timestamp(System.currentTimeMillis()));
				participantRepository.save(participant);
			});
	}

	//레이드 공격 실행(Update)
	@Override
	public Map<String, Integer> attack(ParticipantDTO participantDTO) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(participantDTO.getRaidId())
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(participantDTO.getUserId())
				.build();
		ParticipantId participantId = ParticipantId.builder()
				.raidEntity(raidEntity)
				.userEntity(userEntity)
				.build();
		
	    return participantRepository.findById(participantId)
	        .map(participant -> {
	            int attack = participantDTO.getAttack();
	            if (attack == 0) return null;
	            
	            Map<String, Integer> result = calculateDamage(attack);

	            participant.setAttack(0);
	            participant.setContribution(participant.getContribution() + result.get("damage"));
	            participant.setCreateTime(new Timestamp(System.currentTimeMillis()));
	            participantRepository.save(participant);

	            return result;
	        })
	        .orElse(null);
	}
	
	//공격력 계산 메소드
	private int calculateAttack(String userId, Long raidId) {
		RaidEntity raidEntity = RaidEntity.builder()
				.raidId(raidId)
				.build();
		UserEntity userEntity = UserEntity.builder()
				.userId(userId)
				.build();
		ParticipantId participantId = ParticipantId.builder()
				.raidEntity(raidEntity)
				.userEntity(userEntity)
				.build();
		
		List<String> cardNumList = new ArrayList<>(); //userCardRepository.findCardNumByUserId(userId);
		String merchantId = "1"; //raidRepository.findMerchantIdById(raidId);
		
		Timestamp startTime = participantRepository.findById(participantId)
				//참가자의 생성 시간을 가져옴
			    .map(participant -> participant.getCreateTime()) 
			    //참가자가 없을 경우 현재 시간으로 설정
			    .orElse(new Timestamp(System.currentTimeMillis())); 
		
		Timestamp endTime = new Timestamp(System.currentTimeMillis());//raidRepository.findEndTimeByRaidId(raidId);
		
		return cardUsageHistoryRepository.calculateAttack(cardNumList, merchantId, startTime, endTime);
	}

	//난수로 최종 대미지 결정
	private Map<String, Integer> calculateDamage(int attack) {	
		Map<String, Integer> result = new HashMap<>();
		Random random = new Random();
	
	    //두 개의 주사위를 굴립니다.
	    int dice1 = random.nextInt(6) + 1;
	    int dice2 = random.nextInt(6) + 1;
	        
	    //공격 배수
	    int multiplier = 1; 
	        
	    if (dice1 == dice2) {
	    	if (dice1 == 6) {
	        	multiplier = 3;
	        }else if (dice1 == 1) {
	        	multiplier = 0;
	        }else {
	        	multiplier = 2;
	        }
	    }
	    
	    result.put("dice1", dice1);
	    result.put("dice2", dice2);
	    
	    int damage = attack * multiplier;
	    result.put("damage", damage);
	        
		return result; 
	}

}
