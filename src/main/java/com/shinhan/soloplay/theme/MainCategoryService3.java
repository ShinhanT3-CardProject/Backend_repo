package com.shinhan.soloplay.theme;

import java.util.List;

public interface MainCategoryService3 {
	// 사용자의 ID로 테마 대분류명을 조회
	List<String> getMainCategoryNameByUserId(String userId);
}
