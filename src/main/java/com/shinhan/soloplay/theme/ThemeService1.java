package com.shinhan.soloplay.theme;

import java.util.List;

public interface ThemeService1 {
	
	// 전체 테마 조회
	public List<ThemeSearchDTO1> findAllTheme();
	
	// 전체 테마 조회 (카테고리별 필터링, 공개여부 참)
	public List<ThemeSearchDTO1> findAllThemeFilter(Long themeMainCategoryId);
	
	// 테마 상세조회
	public ThemeSearchDTO1 findThemeDetail(Long themeId);
	
	// 나의 테마 조회
	public List<ThemeSearchDTO1> findMyTheme(String userId);
	
	// 나의 테마 상세조회
	public ThemeSearchDTO1 findMyThemeDetail(Long themeId);
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	public ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1);
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
	public void deleteTheme(Long themeId);
	
	// 테마 등록
	public ThemeRegisterDTO1 insertTheme(ThemeRegisterDTO1 themeRegisterDTO1);

	// 테마 불러오기 (테마 등록, 테마 수정에서 가능)
	public List<ThemeSearchDTO1> loadOtherTheme(Long themeId);

	
}