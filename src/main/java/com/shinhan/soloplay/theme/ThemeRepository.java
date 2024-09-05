package com.shinhan.soloplay.theme;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ThemeRepository extends JpaRepository<ThemeEntity, Long> {
	
	// 전체 테마 조회 (공개여부 참) - 완료
	List<ThemeEntity> findByThemeIsPublicTrue();
	
	// 전체 테마 조회 페이징
	@Query("SELECT t FROM ThemeEntity t WHERE t.themeIsPublic = TRUE")
	Page<ThemeEntity> findAllTheme(Pageable pageable);
	
	// 카테고리별 테마 조회 페이징
	@Query("SELECT DISTINCT t FROM ThemeEntity t "
			+ "JOIN t.themeContents tc "
			+ "JOIN tc.subCategory sc "
			+ "JOIN sc.mainCategory mc " 
			+ "WHERE t.themeIsPublic = TRUE "
			+ "AND mc.themeMainCategoryId = :themeMainCategoryId")
	Page<ThemeEntity> findByCategory(Pageable pageable, @Param("themeMainCategoryId") Long themeMainCategoryId);
	
	// 테마 검색 페이징
	@Query("SELECT t FROM ThemeEntity t WHERE t.themeIsPublic = TRUE AND themeName like :search")
	Page<ThemeEntity> searchByName(Pageable pageable, @Param("search") String search);
	
	// 테마 상세 조회, 나의 테마 상세조회 - 완료
	ThemeEntity findByThemeId(Long themeId);
	
    // 나의 테마 조회 - 완료
	List<ThemeEntity> findByUser_UserId(String userId);
	
	// 테마 불러오기 (테마 등록, 테마 수정에서 가능)
	@Query("SELECT new com.shinhan.soloplay.theme.ThemeSearchDTO(t.themeId, t.user, t.themeName, t.themeDescription, t.themeIsActivated, t.themeIsPublic, t.themeCreateDate, t.themeUpdateDate, mc.themeMainCategoryId, mc.themeMainCategoryName, mc.themeBackground, sc.themeSubCategoryName) " +
				"FROM ThemeEntity t " +
				"JOIN t.themeContents tc " + 
				"JOIN tc.subCategory sc " +
				"JOIN sc.mainCategory mc " + 
				"WHERE t.themeId = :themeId")
	List<ThemeSearchDTO> loadOtherTheme(@Param("themeId") Long themeId);
	
	@Modifying
	@Query("UPDATE ThemeEntity t SET t.themeIsRewarded = TRUE WHERE t.themeId = :themeId")
	int updateThemeIsRewarded(@Param("themeId") Long themeId);
	
	// 유저 ID와 활성화된 테마에 해당하는 테마 ID를 찾는 쿼리
    @Query("SELECT t.themeId FROM ThemeEntity t WHERE t.user.userId = :userId AND t.themeIsActivated = true")
    Long findActivatedThemeIdsByUserId(@Param("userId") String userId);
    
    //유저 ID에 대한 테마 전부 0으로 두기
    @Modifying
    @Transactional
    @Query("UPDATE ThemeEntity t set t.themeIsActivated = false where t.user.userId = :userId")
    void findThemeIsActivated(@Param("userId") String userId);
    
    //유저 ID에 대한 등록된 테마 갯수 확인
    @Query("SELECT count(t.user.userId) from ThemeEntity t where t.user.userId= :userId")
    Long findThemeCount(@Param("userId") String userId);
}