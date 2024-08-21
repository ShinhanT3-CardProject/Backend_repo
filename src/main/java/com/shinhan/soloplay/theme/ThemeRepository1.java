package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThemeRepository1 extends JpaRepository<ThemeEntity, Long> {
	
	// 전체 테마 조회
	@Query("SELECT t from ThemeEntity t")
	List<ThemeSearchDTO1> findAllTheme();
	
	// 전체 테마 조회 (카테고리별 필터링, 공개여부 참)
	@Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO1(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) " +
				"FROM ThemeEntity t " +
				"JOIN t.themeContents tc " +
				"JOIN tc.subCategory sc " + 
				"JOIN sc.mainCategory mc " +
				"WHERE mc.themeMainCategoryId = :themeMainCategoryId AND t.themeIsPublic = true")
	List<ThemeSearchDTO1> findAllThemeFilter(@Param("themeMainCategoryId") Long themeMainCategoryId);
	
	// 테마 상세조회
	@Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO1(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) " +
			"FROM ThemeEntity t " +
			"JOIN t.themeContents tc " +
			"JOIN tc.subCategory sc " + 
			"JOIN sc.mainCategory mc " +
			"WHERE t.themeId=1")
	ThemeSearchDTO1 findThemeDetail(Long themeId);
	
    // 나의 테마 조회
	@Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO1(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) " +
				"FROM ThemeEntity t " +
				"JOIN t.themeContents tc " + 
				"JOIN tc.subCategory sc " +
				"JOIN sc.mainCategory mc " + 
			"WHERE t.user.userId = :userId")
	List<ThemeSearchDTO1> findMyTheme(@Param("userId") String userId);
	
	// 나의 테마 상세조회
    @Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO1(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) FROM ThemeEntity t JOIN t.themeContents tc JOIN tc.subCategory sc JOIN sc.mainCategory mc WHERE t.themeId = :themeId")
    ThemeSearchDTO1 findMyThemeDetail(@Param("themeId") Long themeId);
	
	// 테마 수정 (나의 테마 상세조회에서 가능)
//	@Query("SELECT * from ThemeEntity")
//	ThemeRegisterDTO1 updateTheme(Long themeId, ThemeRegisterDTO1 themeRegisterDTO1);
		
	// 테마 삭제 (나의 테마 상세조회에서 가능)
//	@Query("SELECT * from ThemeEntity")
//	void deleteTheme(Long themeId);
		
	// 테마 등록
//	@Query("SELECT * from ThemeEntity")
//	ThemeRegisterDTO1 insertTheme(ThemeRegisterDTO1 themeRegisterDTO1);

	// 테마 불러오기 (테마 등록, 테마 수정에서 가능)
	@Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO1(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) " +
				"FROM ThemeEntity t " +
				"JOIN t.themeContents tc " + 
				"JOIN tc.subCategory sc " +
				"JOIN sc.mainCategory mc " + 
				"WHERE t.themeId = :themeId")
	List<ThemeSearchDTO1> loadOtherTheme(@Param("themeId") Long themeId);
}