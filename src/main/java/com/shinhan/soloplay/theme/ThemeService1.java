package com.shinhan.soloplay.theme;

import java.util.Map;

public interface ThemeService1 {
	
	// 전체 테마 조회 (공개여부 참) - 완료
	public Map<Long, Map<String, String>> findAllTheme();
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	public Map<String ,?>  findThemeDetail(Long themeId);
	
	// 나의 테마 조회 - 완료
	public Map<Long, Map<String, String>> findMyTheme(String userId);
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	public ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1);
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
	public void deleteTheme(Long themeId);
	
	// 테마 등록
	public ThemeRegisterDTO1 insertTheme(ThemeRegisterDTO1 themeRegisterDTO1);
	
}