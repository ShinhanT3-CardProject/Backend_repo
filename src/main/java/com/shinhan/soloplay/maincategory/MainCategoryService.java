package com.shinhan.soloplay.maincategory;

import java.util.List;

public interface MainCategoryService {
	// 사용자의 ID로 테마 대분류명을 조회
	List<String> getMainCategoryNameByUserId(String userId);
}
