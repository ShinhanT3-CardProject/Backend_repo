package com.shinhan.soloplay.card;

import java.util.List;

public interface UserCardService {
	
	// 사용자의 소지 카드 목록 조회
    List<UserCardDTO> getUserCardsByUserId(String userId);
	
}
