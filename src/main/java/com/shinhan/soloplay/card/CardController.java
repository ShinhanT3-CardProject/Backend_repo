package com.shinhan.soloplay.card;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.soloplay.maincategory.MainCategoryService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/card")
public class CardController {

	@Autowired
	CardService cardService;

	@Autowired
	MainCategoryService mainCategoryService3;

	@Autowired
	UserCardService userCardService;

	@Autowired
	CardUsageHistoryService cardUsageHistoryService;

	// 전체 카드 목록
	@GetMapping("/list")
	public List<CardDTO> list() {
		System.out.println(cardService.getList());
		return cardService.getList();
	}

	// 추천 카드
	@GetMapping("/cardRecommend")
	public Map<String, List<CardDTO>> getCardsByUserTheme(@RequestParam String userId) {
		Map<String, List<CardDTO>> result = new HashMap<>();

		// 1. 유저의 테마 대분류명들 조회
		List<String> mainCategoryNames = mainCategoryService3.getMainCategoryNameByUserId(userId);

		// 2. 사용자 테마리스트 랜덤선택하여, 해당 혜택의 카드 조회
		if (mainCategoryNames != null && !mainCategoryNames.isEmpty()) {
			Random random = new Random();
			String themeName = mainCategoryNames.get(random.nextInt(mainCategoryNames.size())); // 랜덤으로 테마명 선택
			List<CardDTO> cards = cardService.getCardsByType(themeName); // 해당 테마명으로 카드 검색

			result.put(themeName, cards);
		}

		return result; // 테마명과 카드 리스트를 함께 반환
	}

	@GetMapping("/history")
	public Map<String, Map<String, Object>> getUserCardsAndUsageHistory(HttpSession session) {
	    Map<String, Map<String, Object>> result = new HashMap<>();
	    String userId = (String) session.getAttribute("loginUser");

	    // 1. 사용자 소지 카드 목록
	    List<UserCardDTO> userCards = userCardService.getUserCardsByUserId(userId);

	    // 2. 각 카드의 사용 내역 및 카드 이름을 함께 포함
	    for (UserCardDTO userCard : userCards) {
	        Map<String, Object> cardData = new HashMap<>();

	        // 카드 사용 내역 추가
	        List<CardUsageHistoryDTO> usageHistory = cardUsageHistoryService.getCardUsageHistoryByCardNum(userCard.getCardNum());
	        cardData.put("usageHistory", usageHistory);

	        // 카드 이름 추가
	        String cardName = cardService.getCardNameById(userCard.getCardId());
	        cardData.put("cardName", cardName);

	        // 카드번호와 해당 데이터를 결과에 포함시킴
	        result.put(userCard.getCardNum(), cardData);
	    }

	    return result; 

   }  
}
