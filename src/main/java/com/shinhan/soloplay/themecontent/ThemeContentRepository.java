package com.shinhan.soloplay.themecontent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ThemeContentRepository extends JpaRepository<ThemeContentEntity, Long> {

	@Query("SELECT COUNT(tc.themeIsSuccess) FROM ThemeContentEntity tc WHERE tc.themeIsSuccess = TRUE AND tc.theme.themeId = :themeId")
	int countAllByThemeIsSuccessTrue(Long themeId);
	
	@Modifying
	@Query("UPDATE ThemeContentEntity tc SET tc.themeContentIsRewarded = TRUE WHERE tc.themeContentId = :themeContentId")
	int updateIsRewarded(@Param("themeContentId") Long themeContentId);
}
