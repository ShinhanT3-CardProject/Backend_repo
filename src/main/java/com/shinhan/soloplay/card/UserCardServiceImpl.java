package com.shinhan.soloplay.card;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shinhan.soloplay.user.UserEntity;

@Service
public class UserCardServiceImpl implements UserCardService {

	@Autowired
	UserCardRepository userCardRepository;

	@Override
    public List<UserCardDTO> getUserCardsByUserId(String userId) {
		UserEntity user = UserEntity.builder().userId(userId).build();
        return userCardRepository.findByUser(user).stream()
                .map(this::entityToDTO)
                .collect(Collectors.toList());
    }

    private UserCardDTO entityToDTO(UserCardEntity entity) {
        return UserCardDTO.builder()
                .cardNum(entity.getCardNum())
                .cardId(entity.getCard().getCardId())
                .userId(entity.getUser().getUserId())
                .build();
    }

}
