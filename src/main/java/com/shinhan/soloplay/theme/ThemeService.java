package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.data.domain.Page;

import com.shinhan.soloplay.maincategory.MainCategoryEntity;
import com.shinhan.soloplay.subcategory.SubCategoryEntity;

public interface ThemeService {
	
	// 전체 테마 조회 (공개여부 참) - 완료
//	public List<ThemeDetailResponseDTO> findAllTheme();
	
	// 전체 테마 조회 (공개여부 참) - 페이징
	public Page<ThemeDetailResponseDTO> findAllTheme(int page);
	
	// 카테고리별 테마 조회 - 페이징
	public Page<ThemeDetailResponseDTO> findByCategory(int page, Long themeMainCategoryId);
		
	// 테마 검색 - 페이징
	public Page<ThemeDetailResponseDTO> searchByName(int page, String search);
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	public ThemeDetailResponseDTO findThemeDetail(Long themeId);
	
	// 나의 테마 조회 - 완료
	public List<ThemeDetailResponseDTO> findMyTheme(String userId);
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
	public ThemeRegisterDTO updateTheme(Long themeId, ThemeRegisterDTO themeRegisterDTO1, String userID);
	
	// 테마 삭제 (나의 테마 상세조회에서 가능)
	public void deleteTheme(Long themeId);
	
	// 테마 등록
	public ThemeRegisterDTO insertTheme(ThemeRegisterDTO themeRegisterDTO1);
	
	// 테마 성공 여부 확인
	public boolean checkThemeSuccess(Long themeId);
	
	// 테마 성공 보상 수여
	public int updateThemeIsRewarded(Long themeId);
	
	public List<MainCategoryEntity> getAllMainCategories();

    public List<SubCategoryEntity> getSubCategoriesByMainCategory(Long mainCategoryId);

    public int getIsSuccess(String userId);
    
    public void getIsActivated(String userId);
    
    public Long getThemeCount(String userId);
	
}