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

import com.shinhan.soloplay.theme.MainCategoryService3;

 
@RestController
@RequestMapping("/card") 
public class CardController {
	
	@Autowired
	CardService cardService;
	
	@Autowired
	private MainCategoryService3 mainCategoryService3;
	
	@GetMapping("/list")
	public List<CardDTO> list(){ 
		System.out.println(cardService.getList());
		return cardService.getList(); 
	} 	
	
	/*
	 * // 사용자의 테마명 조회
	 * @GetMapping("/mainCategory") public List<String>
	 * getMainCategoryName(@RequestParam String userId) { return
	 * mainCategoryService3.getMainCategoryNameByUserId(userId); }
	 * 
	 * // 해당 테마명의 카드들 조회
	 * @GetMapping("/byType") public List<CardDTO> getCardsByType(@RequestParam
	 * String cardType) { return cardService.getCardsByType(cardType); }
	 */
    
	 // 카드 테마명과 그에 해당하는 카드들을 반환
    @GetMapping("/cardsByUserTheme")
    public Map<String, List<CardDTO>> getCardsByUserTheme(@RequestParam String userId) {
        Map<String, List<CardDTO>> result = new HashMap<>();

        // 1. 유저의 테마 대분류명들 조회
        List<String> mainCategoryNames = mainCategoryService3.getMainCategoryNameByUserId(userId);

        // 2. 해당 테마명 중 하나를 랜덤으로 선택하여 카드들을 검색
        if (mainCategoryNames != null && !mainCategoryNames.isEmpty()) {
            Random random = new Random();
            String themeName = mainCategoryNames.get(random.nextInt(mainCategoryNames.size())); // 랜덤으로 테마명 선택
            List<CardDTO> cards = cardService.getCardsByType(themeName); // 해당 테마명으로 카드 검색

            // 3. 결과를 Map에 추가
            result.put(themeName, cards);
        }

        return result; // 테마명과 카드 리스트를 함께 반환
    }
}
 
