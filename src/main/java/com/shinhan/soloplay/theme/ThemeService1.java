package com.shinhan.soloplay.theme;

import java.util.List;

public interface ThemeService1 {
	
	// 전체 테마 조회 (공개여부 참) - 완료
	public List<ThemeDetailResponseDTO> findAllTheme();
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	public ThemeDetailResponseDTO findThemeDetail(Long themeId);
	
	// 나의 테마 조회 - 완료
	public List<ThemeDetailResponseDTO> findMyTheme(String userId);
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	public ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1, String userID);
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
	public void deleteTheme(Long themeId);
	
	// 테마 등록
	public ThemeRegisterDTO1 insertTheme(ThemeRegisterDTO1 themeRegisterDTO1);
	
	// 테마 성공 여부 확인
	public boolean checkThemeSuccess(Long themeId);
	
	// 테마 성공 보상 수여
	public int updateThemeIsRewarded(Long themeId);
	
}